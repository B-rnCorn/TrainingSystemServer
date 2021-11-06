package web.learning.system.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.User;
import web.learning.system.dto.JwtResponseDto;
import web.learning.system.dto.LoginDto;
import web.learning.system.dto.RegistrationDto;
import web.learning.system.dto.UserDto;

import java.util.List;

public interface UserService {

    JwtResponseDto login(LoginDto loginDto);

    String registration(RegistrationDto registrationDto);

    List<User> findAll();

    String addStudent(String username, UserDetails principal);

    List<UserDto> getStudents(UserDetails principal);

    List<UserDto> getTeachers(UserDetails principal);

    String deleteStudentFromGroup(String username, UserDetails principal);

}
