package web.learning.system.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.learning.system.domain.Solution;
import web.learning.system.domain.Task;
import web.learning.system.dto.MessageResponse;
import web.learning.system.dto.SolutionUpdateDto;
import web.learning.system.dto.TaskCreationDto;

import java.util.List;


public interface SolutionService {

    List<Solution> getSolutionByStudent(String username, UserDetails principal);
    List<Solution> getAllStudentSolutions(UserDetails principal);
    MessageResponse save(Solution solution);
    MessageResponse update(SolutionUpdateDto solutionUpdateDto);
}
