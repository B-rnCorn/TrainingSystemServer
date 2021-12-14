package web.learning.system.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.Task;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.TaskStudentDto;
import web.learning.system.dto.TaskUpdateDto;

import java.util.List;

public interface TaskService {

    MessageResponse save (Task task);

    List<Task> getTasks();

    Task findById(Integer id);

    List<Task> findByTeacher(UserDetails principal);

    MessageResponse deleteById(Integer id);

    List<TaskStudentDto> getStudentTask(UserDetails principal);

    MessageResponse update(TaskUpdateDto taskUpdateDto);

    MessageResponse publishTask(Integer id);
}
