package web.learning.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import web.learning.system.domain.Solution;
import web.learning.system.domain.User;
import web.learning.system.exception.GlobalException;
import web.learning.system.repository.SolutionRepository;
import web.learning.system.repository.UserRepository;
import web.learning.system.service.SolutionService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final UserRepository userRepository;

    @Override
    public List<Solution> getSolutionByStudent(String username, UserDetails principal) {
        User teacher = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + principal.getUsername() + " не существует!", HttpStatus.BAD_REQUEST));
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException("Пользователя с логином: " + username + " не существует!", HttpStatus.BAD_REQUEST));
        return student.getSolutions().stream()
                .filter(solution -> solution.getTask().getAuthor().equals(teacher))
                .collect(Collectors.toList());

    }
}
