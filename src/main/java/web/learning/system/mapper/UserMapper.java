package web.learning.system.mapper;

import web.learning.system.domain.User;
import web.learning.system.dto.UserDto;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFio(user.getFio());
        return userDto;
    }
}
