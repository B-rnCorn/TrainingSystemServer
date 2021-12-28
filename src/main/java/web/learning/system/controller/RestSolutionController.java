package web.learning.system.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.Solution;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.*;
import web.learning.system.exception.GlobalException;
import web.learning.system.mapper.SolutionCreationMapper;
import web.learning.system.mapper.SolutionMapper;
import web.learning.system.mapper.UserMapper;
import web.learning.system.repository.SolutionRepository;
import web.learning.system.repository.TaskRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.SolutionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solution")
@Api(value = "", tags = {"Работа с решениями"})
@CrossOrigin(maxAge = 3600)
public class RestSolutionController {

    private final SolutionService solutionService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SolutionRepository solutionRepository;

    @GetMapping("/student")
    public ResponseEntity<List<SolutionDto>> getStudentSolutions(@RequestParam String username) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Solution> solutions = solutionService.getSolutionByStudent(username, principal);
        return new ResponseEntity<>(SolutionMapper.toSolutionDtoList(solutions), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<SolutionDto>> getAllStudentSolutionsForStudent() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Solution> solutions = solutionService.getAllStudentSolutions(principal);
        return new ResponseEntity<>(SolutionMapper.toSolutionDtoList(solutions), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<MessageResponse> saveSolution(@RequestBody SolutionCreationDto solutionCreationDto) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepository.findById(solutionCreationDto.getTaskId())
                .orElseThrow(() -> new GlobalException("Задания с id: " + solutionCreationDto.getTaskId() + " не существует!", HttpStatus.BAD_REQUEST));
        User author = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(solutionService.save(SolutionCreationMapper.toSolution(solutionCreationDto, author, task)), HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<MessageResponse> updateSolution(@RequestBody SolutionUpdateDto solutionUpdateDto) {
        return new ResponseEntity<>(solutionService.update(solutionUpdateDto), HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<UserDto>> getStudentSolutionsByTeacher(@RequestParam Integer taskId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = solutionService.getAllStudentSolutionByTeacher(taskId, principal)
                .stream().map(solution -> solution.getAuthor())
                .collect(Collectors.toList());
        return new ResponseEntity<>(UserMapper.toUserDtoList(users), HttpStatus.OK);
    }

    @GetMapping("/setMark")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageResponse> setMark(@RequestParam Integer solutionId, @RequestParam Integer mark) {
        return new ResponseEntity<>(solutionService.setMark(solutionId, mark), HttpStatus.OK);
    }

    @GetMapping("/getStudentSol")
    public ResponseEntity<SolutionDto> getSolutionByIdAndTask(@RequestParam Integer userId, @RequestParam Integer taskId) {
        Solution solution = solutionRepository.findAll()
                .stream().filter(s -> s.getTask().getId().equals(taskId) && s.getAuthor().getId().equals(userId) && s.getIsSend().equals(true))
                .findAny().get();
        return new ResponseEntity<>(SolutionMapper.toDto(solution), HttpStatus.OK);
    }

//    @GetMapping("/getStudent")
//    public ResponseEntity<List<UserDto>> getUserList(Integer taskId) {
//        List<User> user = userRepository.findAll()
//                .stream().filter(user -> user.getSolutions().contains())
//    }
}
