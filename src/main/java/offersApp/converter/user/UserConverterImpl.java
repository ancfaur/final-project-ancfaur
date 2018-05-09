package offersApp.converter.user;

import offersApp.dto.UserDto;
import offersApp.entity.Role;
import offersApp.entity.User;
import offersApp.entity.builder.UserBuilder;
import offersApp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterImpl implements UserConverter {
    private RoleRepository roleRepository;

    @Autowired
    public UserConverterImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user = new UserBuilder()
                .setId(userDto.getId())
                .setUsername(userDto.getUsername())
                .setPassword(userDto.getPassword())
                .setRoles(findRoles(userDto.getRole()))
                .build();
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if (user==null) return null;
        UserDto userDto= new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRoles().get(0).getName());
        return userDto;
    }

    @Override
    public List<User> fromDto(List<UserDto> userDtos) {
        List<User> users = new ArrayList<>();
        for(UserDto userDto:userDtos){
            users.add(fromDto(userDto));
        }
        return users;
    }

    @Override
    public List<UserDto> toDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for(User user:users){
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

    private List<Role> findRoles(String name){
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(name);
        roles.add(role);
        return roles;
    }
}