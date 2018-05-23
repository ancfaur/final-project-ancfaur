package offersApp.controller.admin;

import offersApp.dto.CategoryDto;
import offersApp.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value="/admin/manageCategories")
    public String index(Model model){
        model.addAttribute("categoryDtos", categoryService.findAll());
        model.addAttribute("newCategory", new CategoryDto());
        return "adminManageCategories";
    }

    @PostMapping(value="/admin/manageCategories", params="createBtn")
    public String create(@ModelAttribute("newCategory") CategoryDto categoryDto){
        categoryService.create(categoryDto);
        return "redirect:/admin/manageCategories";
    }

    @GetMapping(value="/admin/category/{categoryId}/edit")
    public String indexEdit(Model model, @PathVariable(value = "categoryId") Long categoryId){
        model.addAttribute("categoryDto", categoryService.findById(categoryId));
        return "adminEditDeleteCategory";
    }

    @PostMapping(value="/admin/category/{categoryId}/edit", params="updateBtn")
    public String updateCategory(@PathVariable(value = "categoryId") Long categoryId, @ModelAttribute CategoryDto categoryDto){
        categoryDto.setId(categoryId);
        categoryService.update(categoryDto);
        return "redirect:/admin/manageCategories";
    }

    @PostMapping(value="/admin/category/{categoryId}/edit", params="deleteBtn")
    public String deleteCategory(@PathVariable(value = "categoryId") Long categoryId){
        categoryService.delete(categoryId);
        return "redirect:/admin/manageCategories";
    }

}
