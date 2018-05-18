package offersApp.controller;

import offersApp.service.offer.crud.OfferService;
import offersApp.service.offer.search.OfferSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.CATEGORIES;
import static offersApp.constants.ApplicationConstants.Ordering.ORDERINGS;

@Controller
public class CustomerController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private OfferSearchService offerSearchService;

    @GetMapping(value="/customer/showOffers")
    @Order(value = 1)
    public String index(Model model){
        refillOptions(model);
        model.addAttribute("offerDtos", offerService.findAll());
        return "custOffersTable";
    }

    @PostMapping(value="/customer/showOffers", params = "searchBtn")
    public String searchOffers(@RequestParam String selCategory, @RequestParam String selOrdering, Model model) {
        model.addAttribute("offerDtos", offerSearchService.searchBy(selCategory, selOrdering));
        refillOptions(model);
        return "custOffersTable";
    }

    private void refillOptions(Model model) {
        List<String> categories= new ArrayList<>();
        categories.add("ALL");
        for(String category:CATEGORIES){
            categories.add(category);
        }

        List<String> orderings= new ArrayList<>();
        orderings.add("DATE");
        for(String ordering:ORDERINGS){
            orderings.add(ordering);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("orderings",orderings);
    }
}

