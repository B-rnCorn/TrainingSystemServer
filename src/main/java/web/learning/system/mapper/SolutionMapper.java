package web.learning.system.mapper;

import web.learning.system.domain.Solution;
import web.learning.system.domain.User;
import web.learning.system.dto.SolutionDto;
import web.learning.system.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolutionMapper {

    public static SolutionDto toDto(Solution solution) {
        LocalDateTime date = solution.getDate();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        String newFormatDate = day + "/" +  month + "/" + year;
        return new SolutionDto(solution.getId(), solution.getTask().getTitle(),
                solution.getAlgorithm(), solution.getMark(), newFormatDate, solution.getIsSend(),
                UserMapper.toDto(solution.getAuthor()), TaskMapper.toDto(solution.getTask()));
    }

    public static List<SolutionDto> toSolutionDtoList(List<Solution> solutions) {
        List<SolutionDto> solutionDtoList = new ArrayList<>();
        for (Solution solution: solutions) {
            solutionDtoList.add(toDto(solution));
        }
        return solutionDtoList;
    }
}
