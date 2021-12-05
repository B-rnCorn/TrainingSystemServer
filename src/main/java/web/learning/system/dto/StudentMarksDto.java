package web.learning.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentMarksDto {
    private String username;
    private String fio;
    private List<Integer> marks;
}
