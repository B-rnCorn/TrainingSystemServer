package web.learning.system.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.TaskCreationDto;
import web.learning.system.exception.GlobalException;
import web.learning.system.mapper.TaskCreationMapper;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.TaskService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/task")
@Api(value = "", tags = {"Работа с заданиями"})
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class RestTaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> saveTask(@RequestBody TaskCreationDto taskCreationDto) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userRepository.findByUsername(principal.getUsername())
                    .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Task task = TaskCreationMapper.toTask(taskCreationDto, author);
        return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
    }
}
