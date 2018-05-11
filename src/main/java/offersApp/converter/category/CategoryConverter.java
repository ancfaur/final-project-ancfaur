package offersApp.converter.category;

import offersApp.dto.CategoryDto;
import offersApp.entity.Category;
import offersApp.entity.User;

import java.util.List;

public interface CategoryConverter {
    Category fromDto(CategoryDto categoryDtoDto, List<User> subscribers);
    CategoryDto toDto (Category category);
    List<CategoryDto> toDto (List<Category> categories);
}
