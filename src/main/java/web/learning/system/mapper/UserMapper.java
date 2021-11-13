package web.learning.system.mapper;

import web.learning.system.domain.User;
import web.learning.system.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFio(user.getFio());
        return userDto;
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user: users) {
            userDtoList.add(toDto(user));
        }
        return userDtoList;
    }
}
