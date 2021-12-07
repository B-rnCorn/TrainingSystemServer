package web.learning.system.service;

import web.learning.system.domain.Task;
import web.learning.system.dto.MessageResponse;

public interface TaskService {

    MessageResponse save (Task task);
}
