package web.learning.system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.TaskCreationDto;
import web.learning.system.exception.GlobalException;
import web.learning.system.repository.UserRepository;

import java.time.LocalDateTime;

public class TaskCreationMapper {

    public static Task toTask(TaskCreationDto taskCreationDto, User author) {
        return new Task(taskCreationDto.getTitle(), taskCreationDto.getDescription(), taskCreationDto.getMap(),
                LocalDateTime.now(), false, author);
    }
}
