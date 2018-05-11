package offersApp.service.category;

import offersApp.dto.CategoryDto;
import offersApp.dto.UserDto;
import offersApp.entity.Category;
import java.util.List;


public interface CategoryService{
    CategoryDto create(CategoryDto categoryDto);
    void delete(Long id);
    void update(CategoryDto categoryDto);
    CategoryDto findById(Long id);
    void subscribeUser(String categoryName, Long userId);
    void unsubscribeUser(String categoryName, Long userId);
    CategoryDto findByName(String name);
}
