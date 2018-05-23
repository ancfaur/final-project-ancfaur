package offersApp.controller.admin;
import offersApp.dto.CategoryDto;
import offersApp.dto.ReviewDto;
import offersApp.dto.UserDto;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static offersApp.constants.ApplicationConstants.Roles.AGENT;

@Controller
public class AdminManageAgentController {
    @Autowired
    private UserService userService;

    @GetMapping(value="/admin/manageAgents")
    public String index(Model model){
        model.addAttribute("userDtos", userService.findAllWithRole(AGENT));
        model.addAttribute("newAgent", new UserDto());
        return "adminManageAgents";
    }

    @PostMapping(value="/admin/manageAgents", params="createBtn")
    public String create(@ModelAttribute("newAgent") @Valid UserDto agentDto,  BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDtos", userService.findAllWithRole(AGENT));
            model.addAttribute("newAgent", agentDto);
            return "adminManageAgents";
        }
        agentDto.setRole(AGENT);
        userService.register(agentDto);
        return "redirect:/admin/manageAgents";
    }

    @GetMapping(value="/admin/agent/{agentId}/edit")
    public String updateEditDelete(@PathVariable(value = "agentId") Long agentId, Model model){
        model.addAttribute("userDto",userService.findById(agentId));
        return "adminEditDeleteAgent";
    }

    @PostMapping(value="/admin/agent/{agentId}/edit", params="updateBtn")
    public String updateAgent(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult, Model model,
                              @PathVariable(value = "agentId") Long agentId, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "adminEditDeleteAgent";
        }
        userDto.setRole(AGENT);
        userService.update(userDto);
        redirectAttributes.addFlashAttribute("message", "The agent's info were updated");
        return "redirect:/admin/agent/{agentId}/edit";
    }

    @PostMapping(value="/admin/agent/{agentId}/edit", params="deleteBtn")
    public String deleteAgent(@PathVariable(value = "agentId") Long agentId, RedirectAttributes redirectAttributes){
        userService.delete(agentId);
        redirectAttributes.addFlashAttribute("message", "The agent was successfully deleted");
        return "redirect:/admin/manageAgents";
    }

}
