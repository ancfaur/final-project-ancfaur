package offersApp.controller;

import offersApp.dto.OfferDto;
import offersApp.dto.ReviewDto;
import offersApp.service.offer.basic.OfferService;
import offersApp.service.review.ReviewService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/customer/offer/{offerId}/reviews")
    public String indexReviewTable(Model model, Principal principal, @PathVariable(value = "offerId") Long offerId) {
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
    public String addReview(Principal principal, @PathVariable(value = "offerId") Long offerId, @ModelAttribute ReviewDto reviewDto, RedirectAttributes redirectAttributes) {
        reviewDto.setOfferId(offerId);
        reviewDto.setUserId(userService.findIdForUser(principal.getName()));
        reviewDto.setDate(new Date());
        reviewService.create(reviewDto);

        redirectAttributes.addFlashAttribute("message", "Your review was sent. Thank you for your opinion!");
        return "redirect:/customer/offer/{offerId}/addReview";
    }


    @GetMapping(value = "/customer/offer/{offerId}/review/{reviewId}/edit")
    public String indexEditReview(Model model, Principal principal, @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId) {
        model.addAttribute("offerDto", offerService.findById(offerId));

        ReviewDto reviewDto = reviewService.findById(reviewId);
        if (reviewDto!=null) model.addAttribute("reviewDto", reviewDto);
        else model.addAttribute("reviewDto", new ReviewDto()); // used when, after the review is deleted, the page is redirected here

        return "custEditDeleteReview";
    }

    @PostMapping(value = "/customer/offer/{offerId}/review/{reviewId}/edit", params = "updateBtn")
    public String editReview(Principal principal, @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId, @ModelAttribute ReviewDto reviewDto, RedirectAttributes redirectAttributes) {
        reviewDto.setId(reviewId);
        reviewDto.setOfferId(offerId);
        reviewDto.setUserId(userService.findIdForUser(principal.getName()));
        reviewDto.setDate(new Date());
        reviewService.update(reviewDto);

        redirectAttributes.addFlashAttribute("message", "Your review was updated. Thank you for your opinion!");
        return "redirect:/customer/offer/{offerId}/review/{reviewId}/edit";
    }


    @PostMapping(value ="/customer/offer/{offerId}/review/{reviewId}/edit", params="deleteBtn")
    public String editReview(Principal principal,  @PathVariable(value = "offerId") Long offerId, @PathVariable(value = "reviewId") Long reviewId, RedirectAttributes redirectAttributes) {
        reviewService.delete(reviewId);
        redirectAttributes.addFlashAttribute("message", "Your review was deleted.");
        return "redirect:/customer/offer/{offerId}/review/{reviewId}/edit";
    }
}
