package offersApp.converter.category;

import offersApp.dto.CategoryDto;
import offersApp.entity.Category;
import offersApp.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryConverterImpl implements CategoryConverter{
    @Override
    public Category fromDto(CategoryDto categoryDto, List<User> subscribers) {
        if (categoryDto == null) return null;
        Category category = new Category(categoryDto.getId(), categoryDto.getName(), subscribers);
        return category;
    }

    @Override
    public CategoryDto toDto(Category category) {
        if (category == null) return null;
        CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(), category.getSubscribers().size());
        return categoryDto;
    }

    @Override
    public List<CategoryDto> toDto(List<Category> categories) {
       List<CategoryDto> categoryDtos= new ArrayList<>();
       for (Category category:categories){
           categoryDtos.add(toDto(category));
       }
       return categoryDtos;
    }
}
