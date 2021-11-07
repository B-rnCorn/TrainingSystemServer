package web.learning.system.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.User;
import web.learning.system.dto.*;

import java.util.List;

public interface UserService {

    JwtResponseDto login(LoginDto loginDto);

    MessageResponse registration(RegistrationDto registrationDto);

    List<User> findAll();

    MessageResponse addStudent(String username, UserDetails principal);

    List<UserDto> getStudents(UserDetails principal);

    List<UserDto> getTeachers(UserDetails principal);

    MessageResponse deleteStudentFromGroup(String username, UserDetails principal);

}
