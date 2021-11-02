package web.learning.system.dto;

import lombok.Data;

@Data
public class RegistrationDto {

    private String username;
    private String password;
    private String fio;
    private String role;
}
