package web.learning.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import web.learning.system.domain.Task;
import web.learning.system.dto.MessageResponse;
import web.learning.system.exception.GlobalException;
import web.learning.system.repository.TaskRepository;
import web.learning.system.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

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
}
