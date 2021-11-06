package web.learning.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.User;
import web.learning.system.exception.ObjectNotFoundException;
import web.learning.system.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add_student")
    public ResponseEntity<?> addStudent(@RequestParam String username, Principal principal) {
        try {
            return new ResponseEntity<>(userService.addStudent(username, principal), HttpStatus.OK);

        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/get_students")
    public ResponseEntity<?> getStudents(Principal principal) {
        try {
            return new ResponseEntity<>(userService.getStudents(principal), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/get_teachers")
    public ResponseEntity<?> getTeachers(Principal principal) {
        try {
            return new ResponseEntity<>(userService.getTeachers(principal), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping ("/delete_student")
    public ResponseEntity<?> deleteStudent(@RequestParam String username, Principal principal) {
        try {
            return new ResponseEntity<>(userService.deleteStudentFromGroup(username, principal), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
