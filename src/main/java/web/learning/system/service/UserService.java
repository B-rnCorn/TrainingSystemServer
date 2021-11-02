package web.learning.system.service;

import org.springframework.stereotype.Service;
import web.learning.system.dto.JwtResponseDto;
import web.learning.system.dto.LoginDto;
import web.learning.system.dto.MessageResponseDto;
import web.learning.system.dto.RegistrationDto;
import web.learning.system.exception.ObjectNotFoundException;

public interface UserService {

    JwtResponseDto login(LoginDto loginDto);

    MessageResponseDto registration(RegistrationDto registrationDto) throws ObjectNotFoundException;

}
