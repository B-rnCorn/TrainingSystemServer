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
import web.learning.system.dto.JwtResponseDto;
import web.learning.system.dto.LoginDto;
import web.learning.system.dto.MessageResponseDto;
import web.learning.system.dto.RegistrationDto;
import web.learning.system.exception.ObjectNotFoundException;
import web.learning.system.repository.RoleRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.UserService;

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
    public MessageResponseDto registration(RegistrationDto registrationDto) throws ObjectNotFoundException {
        if (userRepository.existsByUsername(registrationDto.getUsername()))
            return new MessageResponseDto("Ошибка: Данный пользователь уже зарегистрирован!");

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
        return new MessageResponseDto("Пользователь " + user.getUsername() + " успешно зарегистрирован!");
    }
}
