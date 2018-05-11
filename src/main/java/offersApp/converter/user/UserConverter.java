package offersApp.converter.user;

import offersApp.dto.UserDto;
import offersApp.entity.Role;
import offersApp.entity.User;

import java.util.List;

public interface UserConverter {
    User fromDto(UserDto userDto, List<Role> roles);
    UserDto toDto (User user);
    List<UserDto> toDto(List<User> users);
}