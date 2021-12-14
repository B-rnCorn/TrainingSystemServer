package web.learning.system.dto;

import lombok.Data;

@Data
public class TaskCreationDto {
    private String title;
    private String description;
    private String map;
    private Boolean isPublished;
}
