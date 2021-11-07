package web.learning.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.learning.system.dto.JwtResponseDto;
import web.learning.system.dto.LoginDto;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.RegistrationDto;
import web.learning.system.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(value = "", tags = {"Авторизация и регистрация"})
@CrossOrigin(maxAge = 3600)
public class RestAuthController {

    private final UserService userService;

    @PostMapping("/signup")
    @ApiOperation(value = "Регистрация")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationDto registrationDto) {
        return new ResponseEntity<>(userService.registration(registrationDto), HttpStatus.OK);

    }

    @PostMapping("/login")
    @ApiOperation(value = "Авторизация")
    public ResponseEntity<JwtResponseDto> authUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

}
