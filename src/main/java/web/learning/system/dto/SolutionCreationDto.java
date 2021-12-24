package web.learning.system.dto;

import lombok.Data;

@Data
public class SolutionCreationDto {
    private String algorithm;
    private Integer taskId;
    private Boolean isSend;
}
