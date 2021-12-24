package web.learning.system.mapper;

import web.learning.system.domain.Solution;
import web.learning.system.domain.Task;
import web.learning.system.domain.User;
import web.learning.system.dto.SolutionCreationDto;

import java.time.LocalDateTime;

public class SolutionCreationMapper {

    public static Solution toSolution (SolutionCreationDto solutionCreationDto, User author, Task task) {
        return new Solution(solutionCreationDto.getAlgorithm(), null, LocalDateTime.now(), solutionCreationDto.getIsSend(),
                author, task);
    }
}
