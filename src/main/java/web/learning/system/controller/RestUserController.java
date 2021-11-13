package web.learning.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.User;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.UserDto;
import web.learning.system.mapper.UserMapper;
import web.learning.system.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/user")
@Api(value = "", tags = {"Работа с учениками и учителями"})
@CrossOrigin(maxAge = 3600)
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping
    @ApiOperation(value = "Получить всех учеников, которые не состоят в группе")
    public ResponseEntity<List<UserDto>> getAllStudentsWithoutGroup() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(UserMapper.toUserDtoList(userService.getStudentsWithoutGroup(principal)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/add_student")
    @ApiOperation(value = "Добавить ученика в группу (only для учителя)")
    public ResponseEntity<MessageResponse> addStudent(@RequestParam String username) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.addStudent(username, principal), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete_student")
    @ApiOperation(value = "Удалить ученика из группы (only для учителя)")
    public ResponseEntity<MessageResponse> deleteStudent(@RequestParam String username) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.deleteStudentFromGroup(username, principal), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/get_students")
    @ApiOperation(value = "Получить список учеников в группе (only для учителя)")
    public ResponseEntity<List<UserDto>> getStudents() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.getStudents(principal), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/get_teachers")
    @ApiOperation(value = "Получить список учителей (only для ученика)")
    public ResponseEntity<List<UserDto>> getTeachers() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.getTeachers(principal), HttpStatus.OK);
    }

}
