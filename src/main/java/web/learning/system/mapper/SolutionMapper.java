package web.learning.system.mapper;

import web.learning.system.domain.Solution;
import web.learning.system.domain.User;
import web.learning.system.dto.SolutionDto;
import web.learning.system.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class SolutionMapper {

    public static SolutionDto toDto(Solution solution) {
        return new SolutionDto(solution.getId(), solution.getTask().getTitle(),
                solution.getAlgorithm(), solution.getMark(), solution.getDate(), solution.getIsSend());
    }

    public static List<SolutionDto> toSolutionDtoList(List<Solution> solutions) {
        List<SolutionDto> solutionDtoList = new ArrayList<>();
        for (Solution solution: solutions) {
            solutionDtoList.add(toDto(solution));
        }
        return solutionDtoList;
    }
}
