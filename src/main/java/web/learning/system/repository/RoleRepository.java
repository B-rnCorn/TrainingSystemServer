package web.learning.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.learning.system.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
