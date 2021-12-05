package web.learning.system.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.learning.system.config.jwt.JwtUtils;
import web.learning.system.domain.ERole;
import web.learning.system.domain.Role;
import web.learning.system.domain.Solution;
import web.learning.system.domain.User;
import web.learning.system.dto.*;
import web.learning.system.exception.GlobalException;
import web.learning.system.mapper.UserMapper;
import web.learning.system.repository.RoleRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponseDto(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFio(),
                roles);
    }

    @Override
    public MessageResponse registration(RegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername()))
            throw new GlobalException("Ошибка: Данный пользователь уже зарегистрирован!", HttpStatus.BAD_REQUEST);

        User user = new User(registrationDto.getUsername(),
                passwordEncoder.encode(registrationDto.getPassword()),
                registrationDto.getFio());

        String requestRole = registrationDto.getRole();
        Set<Role> roles = new HashSet<>();

        switch (requestRole) {
            case "ROLE_STUDENT":
                Role roleStudent = roleRepository.findByName(ERole.ROLE_STUDENT)
                        .orElseThrow(() -> new GlobalException("Роль 'Ученик' не найдена", HttpStatus.BAD_REQUEST));
                roles.add(roleStudent);
                break;
            case "ROLE_TEACHER":
                Role roleTeacher = roleRepository.findByName(ERole.ROLE_TEACHER)
                        .orElseThrow(() -> new GlobalException("Роль 'Учитель' не найдена", HttpStatus.BAD_REQUEST));
                roles.add(roleTeacher);
                break;
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("Пользователь " + user.getUsername() + " успешно зарегистрирован!");
    }

    @Override
    public List<User> getStudentsWithoutGroup(UserDetails principal) {
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Role role = roleRepository.findByName(ERole.ROLE_TEACHER)
                .orElseThrow(() -> new GlobalException("Роль 'Учитель' не найдена", HttpStatus.BAD_REQUEST));
        return userRepository.findAll().stream().filter(
                user -> !user.getRoles().contains(role) && !user.getTeachers().contains(teacher)
        ).collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public MessageResponse addStudent(String username, UserDetails principal){
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + username + " не существует!", HttpStatus.BAD_REQUEST));
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        teacher.getStudents().add(student);
        userRepository.save(teacher);
        return new MessageResponse("Ученик " + username + " успешно добавлен в группу");
    }

    @Override
    public MessageResponse deleteStudentFromGroup(String username, UserDetails principal){
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + username + " не существует!", HttpStatus.BAD_REQUEST));
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        if (teacher.getStudents().contains(student))
            teacher.getStudents().remove(student);
        else
            throw new GlobalException("Ученик " + username + " не состоит в вашей группе", HttpStatus.BAD_REQUEST);
        userRepository.save(teacher);
        return new MessageResponse("Ученик " + username + " удален из группы");
    }

    @Override
    public List<UserDto> getStudents(UserDetails principal){
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Set<User> students = teacher.getStudents();
        List<UserDto> studentsDto = new ArrayList<>();
        for (User student: students) {
            studentsDto.add(UserMapper.toDto(student));
        }
        return studentsDto;
    }


    @Override
    public List<UserDto> getTeachers(UserDetails principal) {
        User student = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Set<User> teachers = student.getTeachers();
        List<UserDto> teachersDto = new ArrayList<>();
        for (User teacher: teachers) {
            teachersDto.add(UserMapper.toDto(teacher));
        }
        return teachersDto;
    }

    @Override
    public List<StudentMarksDto> getStudentMarks(UserDetails principal) {
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        List<User> students  = teacher.getStudents().stream()
                .filter((user -> user.getTeachers().contains(teacher)))
                .collect(Collectors.toList());
        List<StudentMarksDto> studentMarksDtoList = new ArrayList<>();
        for (User student: students) {
            List<Integer> marks = student.getSolutions().stream()
                    .map(Solution::getMark)
                    .collect(Collectors.toList());
            studentMarksDtoList.add(new StudentMarksDto(student.getUsername(), student.getFio(), marks));
        }

        return studentMarksDtoList;
    }
}
