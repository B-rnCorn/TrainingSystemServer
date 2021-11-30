package web.learning.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("UserDto")
public class SolutionDto {

    private Integer id;
    private String title;
    private String algorithm;
    private Integer mark;
    private LocalDateTime createdDate;
    private Boolean isSend;
}
