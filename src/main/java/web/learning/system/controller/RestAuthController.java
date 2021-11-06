package web.learning.system.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.User;
import web.learning.system.dto.JwtResponseDto;
import web.learning.system.dto.LoginDto;
import web.learning.system.dto.RegistrationDto;
import web.learning.system.exception.ObjectNotFoundException;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class RestAuthController {

    private final UserService userService;

    @Autowired
    public RestAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
            return new ResponseEntity<>(userService.registration(registrationDto), HttpStatus.OK);
        } catch (ObjectNotFoundException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

}
