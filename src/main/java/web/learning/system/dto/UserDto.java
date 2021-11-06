package web.learning.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("UserDto")
public class UserDto {

    private Integer id;
    private String username;
    private String fio;
}
