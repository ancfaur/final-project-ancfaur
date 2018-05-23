package offersApp.controller.customer;

import offersApp.dto.OfferDto;
import offersApp.dto.UserDto;
import offersApp.service.category.CategoryService;
import offersApp.service.user.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static offersApp.constants.ApplicationConstants.Roles.CUSTOMER;

@Controller
public class CustRegisterController {
    @Autowired
    private UserService userService;

    @GetMapping(value="/register")
    @Order(value = 1)
    public String indexRegistration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping(value="/register", params="registerBtn")
    public String register(Model model, @ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", new UserDto());
            return "registration";
        }
        userDto.setRole(CUSTOMER);
        userService.register(userDto);
       return "redirect:/customer/manageCategories";
    }
}
