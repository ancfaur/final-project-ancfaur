package offersApp.controller.customer;

import offersApp.dto.SaleDto;
import offersApp.dto.SearchDto;
import offersApp.service.email.EmailService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.offer.search.OfferSearchService;
import offersApp.service.sale.LimittedStockException;
import offersApp.service.sale.SaleService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.CATEGORIES;
import static offersApp.constants.ApplicationConstants.EmailTemplates.SALE_CONFIRMATION_TYPE;
import static offersApp.constants.ApplicationConstants.Ordering.ORDERINGS;

@Controller
public class CustController {
    private OfferService offerService;
    private OfferSearchService offerSearchService;
    private SaleService saleService;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public CustController(OfferService offerService, OfferSearchService offerSearchService, SaleService saleService, UserService userService, EmailService emailService) {
        this.offerService = offerService;
        this.offerSearchService = offerSearchService;
        this.saleService = saleService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/customer/showOffers")
    @Order(value = 1)
    public String indexOffers(Model model) {
        model.addAttribute("offerDtos", offerService.findAll());
        return "custOffersTable";
    }

    @GetMapping(value = "/customer/crazySearch")
    @Order(value = 1)
    public String indexSearch(Model model) {
        List<String> orderings = new ArrayList<>();
        orderings.add("DATE");
        for (String ordering : ORDERINGS) {
            orderings.add(ordering);
        }
        model.addAttribute("orderings", orderings);
        model.addAttribute("offerDtos", offerService.findAll());
        model.addAttribute("searchDto", new SearchDto());
        return "custCrazySearch";
    }

    @PostMapping(value = "/customer/crazySearch", params = "searchBtn")
    public String searchOffers(@RequestParam("category") List<String> categories,@ModelAttribute SearchDto searchDto, @RequestParam String selOrdering, Model model) {
        searchDto.setOrdering(selOrdering);
        searchDto.setCategories(categories);
        model.addAttribute("offerDtos", offerSearchService.searchBy(searchDto));
        model.addAttribute("message", offerSearchService.searchDescription(searchDto));

        List<String> orderings = new ArrayList<>();
        orderings.add("DATE");
        for (String ordering : ORDERINGS) {
            orderings.add(ordering);
        }
        model.addAttribute("orderings", orderings);
        model.addAttribute("searchDto", searchDto);
        return "custCrazySearch";
    }

    @GetMapping(value = "/customer/offer/{offerId}/buy")
    @Order(value = 1)
    public String indexBuy(Model model, @PathVariable(value = "offerId") Long offerId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        model.addAttribute("saleDto", new SaleDto());
        return "custBuy";
    }

    @PostMapping(value = "/customer/offer/{offerId}/buy", params = "buyBtn")
    public String buyOffer(@PathVariable(value = "offerId") Long offerId, @ModelAttribute @Valid SaleDto saleDto, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerDto", offerService.findById(offerId));
            model.addAttribute("saleDto", saleDto);
            return "custBuy";
        }
        saleDto.setDate(new Date());
        saleDto.setCustomerId(userService.findIdForUser(principal.getName()));
        saleDto.setOfferId(offerId);
        try {
            SaleDto backSaleDto =saleService.sell(saleDto);
            emailService.sendEmail(SALE_CONFIRMATION_TYPE, backSaleDto);
            redirectAttributes.addFlashAttribute("message", "Your order has been placed. You will receive the ticket on your email address. By the way, you have to pay "+backSaleDto.getSumToPay());
        } catch (LimittedStockException e) {
            redirectAttributes.addFlashAttribute("message", "Unfortunately, there aren't as many offers now in stock. Better luck next time!");
        }

    return "redirect:/customer/offer/{offerId}/buy";
    }
}

