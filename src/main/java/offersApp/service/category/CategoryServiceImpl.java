package offersApp.service.category;

import offersApp.converter.category.CategoryConverter;
import offersApp.dto.CategoryDto;
import offersApp.dto.UserDto;
import offersApp.entity.Category;
import offersApp.entity.User;
import offersApp.repository.CategoryRepository;
import offersApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements  CategoryService{
    private CategoryConverter categoryConverter;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public CategoryServiceImpl(CategoryConverter categoryConverter, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryConverter = categoryConverter;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CategoryDto findByName(String name) {
        return categoryConverter.toDto(categoryRepository.findByName(name));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryConverter.toDto(categoryRepository.findAll());
    }

    @Override
    public List<CategoryDto> findCategoriesForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<CategoryDto> results = categoryRepository.findAll().stream()
                .filter(category -> category.hasUserSubscribed(user))
                .map(category-> categoryConverter.toDto(category))
                .collect(Collectors.toList());
        return results;
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryConverter.fromDto(categoryDto, new ArrayList<>());
        Category back = categoryRepository.save(category);
        categoryDto.setId(back.getId());
        return categoryDto;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        categoryRepository.delete(category);
    }

    @Override
    public void update(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElse(null);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryConverter.toDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public void subscribeUser(String name, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Category category =categoryRepository.findByName(name);
        category.addSubscriber(user);
        categoryRepository.save(category);
    }

    @Override
    public void unsubscribeUser(String name, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Category category = categoryRepository.findByName(name);
        category.removeSubscriber(user);
        categoryRepository.save(category);
    }

    @Override
    public void subscribeUser(Long categoryId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.addSubscriber(user);
        categoryRepository.save(category);
    }

    @Override
    public void unsubscribeUser(Long categoryId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.removeSubscriber(user);
        categoryRepository.save(category);
    }
}
