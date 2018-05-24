package offersApp.service.user;
import offersApp.converter.user.UserConverter;
import offersApp.dto.UserDto;
import offersApp.entity.Role;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.ReviewRepository;
import offersApp.repository.RoleRepository;
import offersApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static offersApp.constants.ApplicationConstants.Roles.AGENT;
import static offersApp.constants.ApplicationConstants.Roles.CUSTOMER;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserConverter userConverter;
    private OfferRepository offerRepository;
    private ReviewRepository reviewRepository;
    private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, RoleRepository roleRepository, OfferRepository offerRepository, ReviewRepository reviewRepository){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleRepository = roleRepository;
        this.offerRepository = offerRepository;
        this.reviewRepository = reviewRepository;
    }

    private List<Role> findRoleByName(String roleName){
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName(roleName);
        roles.add(role);
        return roles;
    }

    @Override
    public void update(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userConverter.fromDto(userDto, findRoleByName(userDto.getRole())));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        deleteDependecies(user);
        userRepository.delete(user);
    }

    private void deleteDependecies(User user) {
        if (user.getRoles().get(0).getName().equals(AGENT)){
            offerRepository.deleteByAgent(user);
        }
        if (user.getRoles().get(0).getName().equals(CUSTOMER)){
            reviewRepository.deleteByUser(user);
        }
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
        User user = userConverter.fromDto(userDto, findRoleByName(userDto.getRole()));
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
    public void deleteAll(){
        userRepository.deleteAll();
    }

    @Override
    public boolean checkPasswords(String rawPass, String encodedPass){
        return encoder.matches(rawPass, encodedPass);
    }
}