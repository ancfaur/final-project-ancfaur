package offersApp.controller;

import offersApp.dto.ReviewDto;
import offersApp.service.email.EmailService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.review.ReviewService;
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
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.EmailTemplates.REVIEW_NOTIFICATION_TYPE;

@Controller
public class ReviewController {
    private ReviewService reviewService;
    private OfferService offerService;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public ReviewController(ReviewService reviewService, OfferService offerService, UserService userService, EmailService emailService) {
        this.reviewService = reviewService;
        this.offerService = offerService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/customer/offer/{offerId}/reviews")
    public String indexReviewTable(Model model, Principal principal, @PathVariable(value = "offerId") Long offerId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        List<ReviewDto> reviewDtos = reviewService.findReviewsForOffer(offerId);
        model.addAttribute("reviewDtos", reviewDtos);
        model.addAttribute("nowLoggedIn", principal.getName());
        return "custReviewsTable";
    }

    @GetMapping(value = "/customer/offer/{offerId}/addReview")
    public String indexAddReview(Model model, @PathVariable(value = "offerId") Long offerId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        model.addAttribute("reviewDto", new ReviewDto());
        return "custAddReview";
    }

    @PostMapping(value = "/customer/offer/{offerId}/addReview", params = "sendBtn")
    public String createReview(Principal principal, @PathVariable(value = "offerId") Long offerId,
                            @ModelAttribute @Valid ReviewDto reviewDto, BindingResult bindingResult, Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerDto", offerService.findById(offerId));
            model.addAttribute("reviewDto", reviewDto);
            return "custAddReview";
        }

        reviewDto.setOfferId(offerId);
        reviewDto.setUserId(userService.findIdForUser(principal.getName()));
        reviewDto.setDate(new Date());
        ReviewDto backReviewDto =reviewService.create(reviewDto);
        emailService.sendEmail(REVIEW_NOTIFICATION_TYPE, backReviewDto);


        redirectAttributes.addFlashAttribute("message", "Your review was sent. Thank you for your opinion!");
        return "redirect:/customer/offer/{offerId}/addReview";
    }


    @GetMapping(value = "/customer/offer/{offerId}/review/{reviewId}/edit")
    public String indexEditReview(Model model, Principal principal, @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        model.addAttribute("reviewDto", reviewService.findById(reviewId));
        return "custEditDeleteReview";
    }

    @PostMapping(value = "/customer/offer/{offerId}/review/{reviewId}/edit", params = "updateBtn")
    public String editReview(Principal principal, @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId,
                             @ModelAttribute @Valid ReviewDto reviewDto,BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerDto", offerService.findById(offerId));
            reviewDto.setId(reviewId);
            model.addAttribute("reviewDto", reviewDto);
            return "custEditDeleteReview";
        }

        reviewDto.setId(reviewId);
        reviewDto.setOfferId(offerId);
        reviewDto.setUserId(userService.findIdForUser(principal.getName()));
        reviewDto.setDate(new Date());
        reviewService.update(reviewDto);

        redirectAttributes.addFlashAttribute("message", "Your review was updated. Thank you for your opinion!");
        return "redirect:/customer/offer/{offerId}/review/{reviewId}/edit";
    }


    @PostMapping(value ="/customer/offer/{offerId}/review/{reviewId}/edit", params="deleteBtn")
    public String deleteReview(@PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId) {
        reviewService.delete(reviewId);
        return "redirect:/customer/offer/{offerId}/reviews";
    }


    @GetMapping(value = "/agent/offer/{offerId}/review/{reviewId}/view")
    public String indexViewReview(Model model, Principal principal, @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId) {
        model.addAttribute("offerDto", offerService.findById(offerId));
        model.addAttribute("reviewDto", reviewService.findById(reviewId));
        return "agentViewReview";
    }
}
