package web.learning.system.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.Solution;

import java.util.List;
import java.util.Set;

public interface SolutionService {

    List<Solution> getSolutionByStudent(String username, UserDetails principal);
    List<Solution> getAllStudentSolutions(UserDetails principal);
}
