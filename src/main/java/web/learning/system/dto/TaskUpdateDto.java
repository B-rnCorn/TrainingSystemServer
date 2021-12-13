package web.learning.system.dto;

import lombok.Data;

@Data
public class TaskUpdateDto {
    private Integer id;
    String title;
    String description;
    String map;
}
