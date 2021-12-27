package web.learning.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import web.learning.system.domain.Solution;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.SolutionUpdateDto;
import web.learning.system.exception.GlobalException;
import web.learning.system.repository.SolutionRepository;
import web.learning.system.repository.TaskRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.SolutionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<Solution> getSolutionByStudent(String username, UserDetails principal) {
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + username + " не существует!", HttpStatus.BAD_REQUEST));
        return student.getSolutions().stream()
                .filter(solution -> solution.getTask().getAuthor().equals(teacher))
                .collect(Collectors.toList());

    }

    @Override
    public List<Solution> getAllStudentSolutions(UserDetails principal) {
        return solutionRepository.findAll().stream()
                .filter(solution -> solution.getAuthor()
                        .equals(userRepository.findByUsername(principal.getUsername())
                        .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST))))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse save(Solution solution) {
        solutionRepository.save(solution);
        return new MessageResponse("Решение успешно сохранено");
    }

    @Override
    public MessageResponse update(SolutionUpdateDto solutionUpdateDto) {
        Solution solution = solutionRepository.findById(solutionUpdateDto.getId())
                .orElseThrow(() -> new GlobalException("Решения с id: " + solutionUpdateDto.getId() + " не существует!", HttpStatus.BAD_REQUEST));
        solution.setAlgorithm(solutionUpdateDto.getAlgorithm());
        solution.setDate(LocalDateTime.now());
        solution.setIsSend(solutionUpdateDto.getIsSend());
        solutionRepository.save(solution);
        return new MessageResponse("Решение успешно изменено");
    }

    @Override
    public List<Solution> getAllStudentSolutionByTeacher(Integer taskId, UserDetails principal) {
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new GlobalException("Задания с id: " + taskId + " не существует!", HttpStatus.BAD_REQUEST));
        return solutionRepository.findAll().stream()
                .filter(solution -> solution.getTask().getAuthor().equals(teacher) && solution.getTask().equals(task))
                .collect(Collectors.toList());
    }
}
