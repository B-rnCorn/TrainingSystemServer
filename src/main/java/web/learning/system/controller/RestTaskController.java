package web.learning.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.*;
import web.learning.system.exception.GlobalException;
import web.learning.system.mapper.TaskCreationMapper;
import web.learning.system.mapper.TaskMapper;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

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
    @ApiOperation(value = "Сохранить задание")
    public ResponseEntity<MessageResponse> saveTask(@RequestBody TaskCreationDto taskCreationDto) {
        boolean isPublished;
        isPublished = taskCreationDto.getIsPublished() != null;
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userRepository.findByUsername(principal.getUsername())
                    .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Task task = TaskCreationMapper.toTask(taskCreationDto, author, isPublished);
        return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Получить все все задания")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return new ResponseEntity<>(TaskMapper.toDtoList(taskService.getTasks()), HttpStatus.OK);
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    @ApiOperation(value = "Получить все задания учителя")
    public ResponseEntity<List<TaskDto>> getTaskByTeacher() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(TaskMapper.toDtoList(taskService.findByTeacher(principal)), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Получить задание по id")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer id) {
        return new ResponseEntity<>(TaskMapper.toDto(taskService.findById(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить задание по id")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    @ApiOperation(value = "Получить все задания ученика и решения к ним, если они есть")
    public ResponseEntity<List<TaskStudentDto>> getStudentTaskForStudent() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(taskService.getStudentTask(principal), HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> updateTask(@RequestBody TaskUpdateDto taskUpdateDto) {
        return new ResponseEntity<>(taskService.update(taskUpdateDto), HttpStatus.OK);
    }

    @PostMapping("/publish/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> publishTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.publishTask(id), HttpStatus.OK);
    }
}
