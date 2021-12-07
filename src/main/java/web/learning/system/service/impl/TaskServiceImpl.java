package web.learning.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.learning.system.domain.Task;
import web.learning.system.dto.MessageResponse;
import web.learning.system.repository.TaskRepository;
import web.learning.system.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public MessageResponse save(Task task) {
        taskRepository.save(task);
        return new MessageResponse("Задание успешно сохранено");
    }
}
