package offersApp.controller;

import offersApp.dto.OfferDto;
import offersApp.service.imageStorage.StorageService;
import offersApp.service.offer.basic.OfferService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class ManageOfferController {
    private OfferService offerService;
    private StorageService storageService;
    private UserService userService;

    @Autowired
    public ManageOfferController(OfferService offerService, StorageService storageService, UserService userService) {
        this.offerService = offerService;
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping(value="/agent/offer/create")
    @Order(value = 1)
    public String index(Model model) {
        model.addAttribute(new OfferDto());
        return "agentCreateOffer";
    }

    @PostMapping(value="/agent/offer/create", params = "createBtn")
    public String createOffer(@RequestParam("file") MultipartFile file,
                              @ModelAttribute OfferDto offerDto,
                              @RequestParam("category") List<String> categories,
                              @RequestParam("description") String description,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {

        // store image
       String fileRelativePath =storageService.store(file);


        // store offer in the database
        offerDto.setCategories(categories);
        offerDto.setDescription(description);
        offerDto.setImage(fileRelativePath);
        offerDto.setDatePublished(new Date());
        offerDto.setInStock(offerDto.getInitialNo());

        offerDto.setAgentId(userService.findIdForUser(principal.getName()));
        offerService.create(offerDto);
        redirectAttributes.addFlashAttribute("message", "You successfully created an offer"  + "!");

        return "redirect:/agent/offer/create";
    }


    @GetMapping(value="/agent/offer/update/{offerId}")
    public String updateOffer(Model model, @PathVariable(value = "offerId") Long offerId) {
        OfferDto offerDto = offerService.findById(offerId);
        System.out.print("offer id isssss"+offerId);
        model.addAttribute("offerDto", offerDto);
        return "agentUpdateOffer";
    }


    @PostMapping(value="/agent/offer/update/{offerId}", params = "updateBtn")
    public String updateOffer(@RequestParam("file") MultipartFile file,
                              @ModelAttribute OfferDto offerDto,
                              @RequestParam("categoryUpdated") List<String> categories,
                              @RequestParam("descriptionUpdated") String description,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {

        // store image
        if (!file.isEmpty()) {
            String fileRelativePath = storageService.store(file);
            offerDto.setImage(fileRelativePath);
        }

        // store offer in the database
        offerDto.setCategories(categories);
        offerDto.setDescription(description);
        offerDto.setDatePublished(new Date());
        offerDto.setInStock(offerDto.getInitialNo());
        offerDto.setAgentId(userService.findIdForUser(principal.getName()));
        offerService.update(offerDto);
        redirectAttributes.addFlashAttribute("message", "You successfully updated the offer"  + "!");
        return "redirect:/agent/offer/update/{offerId}";
    }


}
