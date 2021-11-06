package web.learning.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.learning.system.config.jwt.JwtUtils;
import web.learning.system.domain.ERole;
import web.learning.system.domain.Role;
import web.learning.system.domain.User;
import web.learning.system.dto.*;
import web.learning.system.exception.ObjectNotFoundException;
import web.learning.system.mapper.UserMapper;
import web.learning.system.repository.RoleRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

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
    public String registration(RegistrationDto registrationDto) throws ObjectNotFoundException {
        if (userRepository.existsByUsername(registrationDto.getUsername()))
            return "Ошибка: Данный пользователь уже зарегистрирован!";

        User user = new User(registrationDto.getUsername(),
                passwordEncoder.encode(registrationDto.getPassword()),
                registrationDto.getFio());

        String requestRole = registrationDto.getRole();
        Set<Role> roles = new HashSet<>();

        switch (requestRole) {
            case "student":
                Role roleStudent = roleRepository.findByName(ERole.ROLE_STUDENT)
                        .orElseThrow(() -> new ObjectNotFoundException("Роль 'Ученик' не найдена"));
                roles.add(roleStudent);
                break;
            case "teacher":
                Role roleTeacher = roleRepository.findByName(ERole.ROLE_TEACHER)
                        .orElseThrow(() -> new ObjectNotFoundException("Роль 'Учитель' не найдена"));
                roles.add(roleTeacher);
                break;
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "Пользователь " + user.getUsername() + " успешно зарегистрирован!";
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String addStudent(String username, Principal principal) throws ObjectNotFoundException {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + username + " не существует!"));
        User teacher = userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + principal.getName() + " не существует!"));
        teacher.getStudents().add(student);
        userRepository.save(teacher);
        return "Ученик " + username + " успешно добавлен в группу";
    }

    @Override
    public String deleteStudentFromGroup(String username, Principal principal) throws ObjectNotFoundException {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + username + " не существует!"));
        User teacher = userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + principal.getName() + " не существует!"));
        teacher.getStudents().remove(student);
        userRepository.save(teacher);
        return "Ученик " + username + " удален из группы";
    }

    @Override
    public List<UserDto> getStudents(Principal principal) throws ObjectNotFoundException {
        User teacher = userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + principal.getName() + " не существует!"));
        Set<User> students = teacher.getStudents();
        List<UserDto> studentsDto = new ArrayList<>();
        for (User student: students) {
            studentsDto.add(UserMapper.toDto(student));
        }
        return studentsDto;
    }


    @Override
    public List<UserDto> getTeachers(Principal principal) throws ObjectNotFoundException {
        User student = userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->new ObjectNotFoundException("Пользователя с логином: " + principal.getName() + " не существует!"));
        Set<User> teachers = student.getTeachers();
        List<UserDto> teachersDto = new ArrayList<>();
        for (User teacher: teachers) {
            teachersDto.add(UserMapper.toDto(teacher));
        }
        return teachersDto;


    }
}
