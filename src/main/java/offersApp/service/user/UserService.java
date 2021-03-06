package offersApp.service.user;
import offersApp.dto.UserDto;
import offersApp.entity.Role;

import java.util.List;

public interface UserService {
    void update(UserDto userDto);
    void delete(Long id);
    List<UserDto> findAll();
    List<UserDto> findAllWithRole(String roleName);
    UserDto register(UserDto userDto);
    Long findIdForUser(String username);

    // for testing purposes
    UserDto findById(Long id);
    void deleteAll();
    boolean checkPasswords(String rawPass, String encodedPass);
}