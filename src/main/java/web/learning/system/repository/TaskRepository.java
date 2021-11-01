package web.learning.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.learning.system.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
