package offersApp.service.user;
import offersApp.converter.user.UserConverter;
import offersApp.dto.UserDto;
import offersApp.entity.User;
import offersApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserConverter userConverter;
    private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }


    @Override
    public void update(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userConverter.fromDto(userDto));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userConverter.toDto(users);
        return userDtos;
    }

    @Override
    public List<UserDto> findAllWithRole(String roleName) {
        List<UserDto> results = userRepository.findAll().stream()
                .filter(user -> user.getRoles().get(0).getName().equals(roleName))
                .map(user->userConverter.toDto(user))
                .collect(Collectors.toList());
        return results;
    }

    @Override
    public UserDto register(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User user = userConverter.fromDto(userDto);
        User back = userRepository.save(user);
        userDto.setId(back.getId());
        return userDto;
    }

    @Override
    public Long findIdForUser(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    // for testing purposes
    @Override
    public UserDto findById(Long id){
        User user =userRepository.findById(id).orElse(null);
        return userConverter.toDto(user);
    }

    @Override
    public void removeAll(){
        userRepository.deleteAll();
    }

    @Override
    public boolean checkPasswords(String rawPass, String encodedPass){
        return encoder.matches(rawPass, encodedPass);
    }
}