package offersApp.controller.customer;

import offersApp.dto.SaleDto;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.offer.search.OfferSearchService;
import offersApp.service.sale.LimittedStockException;
import offersApp.service.sale.SaleService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.CATEGORIES;
import static offersApp.constants.ApplicationConstants.Ordering.ORDERINGS;

@Controller
public class CustController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferSearchService offerSearchService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/customer/showOffers")
    @Order(value = 1)
    public String indexOffers(Model model) {
        refillOptions(model);
        model.addAttribute("offerDtos", offerService.findAll());
        return "custOffersTable";
    }

    @PostMapping(value = "/customer/showOffers", params = "searchBtn")
    public String searchOffers(@RequestParam String selCategory, @RequestParam String selOrdering, Model model) {
        model.addAttribute("offerDtos", offerSearchService.searchBy(selCategory, selOrdering));
        refillOptions(model);
        return "custOffersTable";
    }

    private void refillOptions(Model model) {
        List<String> categories = new ArrayList<>();
        categories.add("ALL");
        for (String category : CATEGORIES) {
            categories.add(category);
        }

        List<String> orderings = new ArrayList<>();
        orderings.add("DATE");
        for (String ordering : ORDERINGS) {
            orderings.add(ordering);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("orderings", orderings);
    }

    @GetMapping(value = "/customer/offer/{offerId}/buy")
    @Order(value = 1)
    public String indexBuy(Model model, @PathVariable(value = "offerId") Long offerId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        model.addAttribute("saleDto", new SaleDto());
        return "custBuy";
    }

    @PostMapping(value = "/customer/offer/{offerId}/buy", params = "buyBtn")
    public String buyOffer(@PathVariable(value = "offerId") Long offerId, @ModelAttribute SaleDto saleDto, Principal principal, RedirectAttributes redirectAttributes) {
        saleDto.setDate(new Date());
        saleDto.setCustomerId(userService.findIdForUser(principal.getName()));
        saleDto.setOfferId(offerId);
        try {
            float sum=saleService.sellAndNotify(saleDto);
            redirectAttributes.addFlashAttribute("message", "Your order has been placed. You will receive the ticket on your email address. By the way, you have to pay "+sum);
        } catch (LimittedStockException e) {
            redirectAttributes.addFlashAttribute("message", "Unfortunately, there aren't as many offers now in stock. Better luck next time!");
        }

    return "redirect:/customer/offer/{offerId}/buy";
    }
}

