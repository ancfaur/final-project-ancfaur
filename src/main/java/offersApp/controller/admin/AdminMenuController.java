package offersApp.controller.admin;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value ="/admin/menu")
public class AdminMenuController {

    @GetMapping()
    @Order(value = 1)
    public String index() {
        return "adminMenu";
    }

    @PostMapping(params = "agentManageBtn")
    public String agentManage() {
        return "redirect:/admin/manageAgents";
    }

    @PostMapping(params = "categoryManageBtn")
    public String categoryManage() {
        return "redirect:/admin/manageCategories";
    }


}