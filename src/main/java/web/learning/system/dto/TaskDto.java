package web.learning.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private String map;
    private String date;
    private Boolean isPublished;
    private UserDto author;
}
