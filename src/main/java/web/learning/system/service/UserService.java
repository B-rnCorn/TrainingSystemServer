package web.learning.system.service;

import org.springframework.stereotype.Service;
import web.learning.system.domain.User;
import web.learning.system.dto.*;
import web.learning.system.exception.ObjectNotFoundException;

import java.security.Principal;
import java.util.List;

public interface UserService {

    JwtResponseDto login(LoginDto loginDto);

    MessageResponseDto registration(RegistrationDto registrationDto) throws ObjectNotFoundException;

    List<User> findAll();

    String addStudent(String username, Principal principal) throws ObjectNotFoundException;

    List<UserDto> getStudents(Principal principal) throws ObjectNotFoundException;

    List<UserDto> getTeachers(Principal principal) throws ObjectNotFoundException;

    String deleteStudentFromGroup(String username, Principal principal) throws ObjectNotFoundException;

}
