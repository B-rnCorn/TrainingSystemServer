package web.learning.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.learning.system.domain.Solution;
import web.learning.system.dto.SolutionDto;
import web.learning.system.mapper.SolutionMapper;
import web.learning.system.service.SolutionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/solution")
@CrossOrigin(maxAge = 3600)
public class RestSolutionController {

    private final SolutionService solutionService;

    @GetMapping("/student")
    public ResponseEntity<List<SolutionDto>> getStudentSolutions(@RequestParam String username) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Solution> solutions = solutionService.getSolutionByStudent(username, principal);
        return new ResponseEntity<>(SolutionMapper.toSolutionDtoList(solutions), HttpStatus.OK);
    }

}
