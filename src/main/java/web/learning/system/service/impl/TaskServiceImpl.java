package web.learning.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import web.learning.system.domain.Solution;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.TaskDto;
import web.learning.system.dto.TaskStudentDto;
import web.learning.system.exception.GlobalException;
import web.learning.system.mapper.SolutionMapper;
import web.learning.system.mapper.TaskMapper;
import web.learning.system.repository.TaskRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse save(Task task) {
        taskRepository.save(task);
        return new MessageResponse("Задание успешно сохранено");
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Задания с id: " + id + " не существует", HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<Task> findByTeacher(UserDetails principal) {
        return getTasks().stream()
                .filter(task -> task.getAuthor().getUsername().equals(principal.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse deleteById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Задания с id: " + id + " не существует", HttpStatus.BAD_REQUEST));
        taskRepository.deleteById(id);
        return new MessageResponse("Задание " + "'" + task.getTitle() + "'" + " успешно удалено");
    }

    @Override
    public List<TaskStudentDto> getStudentTask(UserDetails principal) {
        User student = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        List<Task> tasks = taskRepository.findAll();
        List<TaskStudentDto> tasksStudentDto = new ArrayList<>();
        List<Task> studentTasks = tasks
                .stream()
                .filter(task -> task.getAuthor().getStudents().contains(student))
                .collect(Collectors.toList());
        for (Task task: studentTasks) {
            Solution newSolution = null;
            Optional<Solution> s = task.getSolutions()
                    .stream().filter(solution -> solution.getAuthor().equals(student))
                    .findFirst();
            if (s.isPresent()) {
                newSolution = s.get();
                tasksStudentDto.add(new TaskStudentDto(TaskMapper.toDto(task), SolutionMapper.toDto(newSolution)));
            } else {
                tasksStudentDto.add(new TaskStudentDto(TaskMapper.toDto(task), null));
            }
        }
        return tasksStudentDto;
    }
}
