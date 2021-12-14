package web.learning.system.dto;

import lombok.Data;

@Data
public class TaskUpdateDto {
    private Integer id;
    private String title;
    private String description;
    private String map;
}
