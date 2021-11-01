package web.learning.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.learning.system.domain.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
}
