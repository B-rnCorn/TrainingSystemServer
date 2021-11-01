package web.learning.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.learning.system.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
