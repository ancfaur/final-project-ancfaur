package offersApp.converter.user;

import offersApp.dto.UserDto;
import offersApp.entity.Role;
import offersApp.entity.User;
import offersApp.entity.builder.UserBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public User fromDto(UserDto userDto, List<Role> roles) {
        if(userDto==null) return null;
        User user = new UserBuilder()
                .setId(userDto.getId())
                .setUsername(userDto.getUsername())
                .setPassword(userDto.getPassword())
                .setRoles(roles)
                .setEmail(userDto.getEmail())
                .build();
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if (user==null) return null;
        UserDto userDto= new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRoles().get(0).getName(), user.getEmail());
        return userDto;
    }

    @Override
    public List<UserDto> toDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for(User user:users){
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

}