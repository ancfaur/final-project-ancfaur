package offersApp.controller.customer;
import offersApp.dto.CategoryDto;
import offersApp.service.category.CategoryService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CustCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping(value="/customer/manageCategories")
    @Order(value = 1)
    public String indexCategoryChose(Model model, Principal principal) {
        Long userId = userService.findIdForUser(principal.getName());
        List<CategoryDto> allCategories = categoryService.findAll();
        List<CategoryDto> userCategories = categoryService.findCategoriesForUser(userId);

        for(CategoryDto categoryDto: allCategories){
            if (userCategories.contains(categoryDto)) {
                categoryDto.setChecked(true);
            }
        }
        model.addAttribute("categoryDtos", allCategories);
        return "custCategories";
    }

    @GetMapping(value="/customer/subscribe/{categoryId}")
    public String indexSubscribe(Model model, @PathVariable(value = "categoryId") Long categoryId) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        model.addAttribute(categoryDto);
        return "custCategorySubscribe";
    }

    @PostMapping(value="/customer/subscribe/{categoryId}", params="confirmBtn")
    public String subscribe(Model model, @PathVariable(value = "categoryId") Long categoryId, Principal principal){
        Long userId = userService.findIdForUser(principal.getName());
        categoryService.subscribeUser(categoryId, userId);
        return"redirect:/customer/manageCategories";
    }

    @GetMapping(value="/customer/unsubscribe/{categoryId}")
    public String indexUnsubscribe(Model model, @PathVariable(value = "categoryId") Long categoryId) {
        CategoryDto categoryDto = categoryService.findById(categoryId);
        model.addAttribute(categoryDto);
        return "custCategoryUnsubscribe";
    }

    @PostMapping(value="/customer/unsubscribe/{categoryId}", params="confirmBtn")
    public String unsubscribe(Model model, @PathVariable(value = "categoryId") Long categoryId, Principal principal){
        Long userId = userService.findIdForUser(principal.getName());
        categoryService.unsubscribeUser(categoryId, userId);
        return"redirect:/customer/manageCategories";
    }

}
