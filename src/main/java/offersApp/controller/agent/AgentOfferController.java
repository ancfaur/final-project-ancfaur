package offersApp.controller.agent;

import offersApp.dto.OfferDto;
import offersApp.service.email.EmailService;
import offersApp.service.imageStorage.StorageService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.EmailTemplates.OFFER_NOTIFICATION_TYPE;

@Controller
public class AgentOfferController {
    private OfferService offerService;
    private StorageService storageService;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public AgentOfferController(OfferService offerService, StorageService storageService, UserService userService, EmailService emailService) {
        this.offerService = offerService;
        this.storageService = storageService;
        this.userService = userService;
        this.emailService = emailService;
    }
    @GetMapping(value="/agent/offer/showAll")
    public String showAllOffers(Model model, Principal principal) {
        List<OfferDto> offerDtos =offerService.findOffersForAgent(principal.getName());
        model.addAttribute("offerDtos", offerDtos);
        return "agentOffersTable";
    }

    @GetMapping(value="/agent/offer/create")
    @Order(value = 1)
    public String index(Model model) {
        model.addAttribute("offerDto", new OfferDto());
        return "agentCreateOffer";
    }

    @PostMapping(value="/agent/offer/create", params = "createBtn")
    public String createOffer(@RequestParam("file") MultipartFile file,
                              @ModelAttribute @Valid OfferDto offerDto,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam("category") List<String> categories,
                              @RequestParam("description") String description,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerDto", offerDto);
            return "agentCreateOffer";
        }
        // store image
       String fileName =storageService.store(file);
        // store offer in the database
        offerDto.setCategories(categories);
        offerDto.setDescription(description);
        offerDto.setImage(fileName);
        offerDto.setDatePublished(new Date());
        offerDto.setInStock(offerDto.getInitialNo());

        offerDto.setAgentId(userService.findIdForUser(principal.getName()));
        OfferDto offerDtoBack = offerService.create(offerDto);
        emailService.sendEmail(OFFER_NOTIFICATION_TYPE, offerDtoBack);

        redirectAttributes.addFlashAttribute("message", "You successfully created an offer"  + "!");

        return "redirect:/agent/offer/create";
    }


    @GetMapping(value="/agent/offer/update/{offerId}")
    public String updateOffer(Model model, @PathVariable(value = "offerId") Long offerId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        return "agentEditDeleteOffer";
    }


    @PostMapping(value="/agent/offer/update/{offerId}", params = "updateBtn")
    public String updateOffer(@RequestParam("file") MultipartFile file,
                              @ModelAttribute @Valid OfferDto offerDto,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam("categoryUpdated") List<String> categories,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerDto", offerDto);
            return "agentEditDeleteOffer";
        }

        // store image
        if (!file.isEmpty()) {
            String fileRelativePath = storageService.store(file);
            offerDto.setImage(fileRelativePath);
        }

        // store offer in the database
        offerDto.setCategories(categories);
        offerDto.setDatePublished(new Date());
        offerDto.setInStock(offerDto.getInitialNo());
        offerDto.setAgentId(userService.findIdForUser(principal.getName()));
        offerService.update(offerDto);
        redirectAttributes.addFlashAttribute("message", "You successfully updated the offer"  + "!");
        return "redirect:/agent/offer/update/{offerId}";
    }

    @PostMapping(value="/agent/offer/update/{offerId}", params = "deleteBtn")
    public String deleteOffer(@PathVariable(value = "offerId") Long offerId) {
        offerService.delete(offerId);
        return "redirect:/agent/offer/showAll";
    }
}
