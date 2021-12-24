package web.learning.system.dto;

import lombok.Data;

@Data
public class SolutionUpdateDto {

    private Integer id;
    private String algorithm;
    private Boolean isSend;
}
