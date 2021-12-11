package web.learning.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskStudentDto {
    private TaskDto taskDto;
    private SolutionDto solutionDto;
}
