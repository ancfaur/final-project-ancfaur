package offersApp.controller.agent;

import offersApp.dto.OfferDto;
import offersApp.service.imageStorage.StorageService;
import offersApp.service.offer.manage.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RequestMapping(value="/agent/menu")
@Controller
public class AgentMenuController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private StorageService storageService;

    @GetMapping()
    @Order(value = 1)
    public String index() {
        return "agentMenu";
    }

    @PostMapping(params = "createOfferBtn")
    public String createOffer() {
        return "redirect:/agent/offer/create";
    }

    @PostMapping(params = "showOffersBtn")
    public String showAllOffers() {
        return "redirect:/agent/offer/showAll";
    }


    @PostMapping(value = "/logout", params="logoutBtn")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
