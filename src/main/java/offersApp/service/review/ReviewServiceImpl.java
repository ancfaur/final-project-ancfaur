package offersApp.service.review;

import offersApp.converter.offer.ReviewConverter;
import offersApp.dto.ReviewDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.entity.Offer;
import offersApp.entity.Review;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.ReviewRepository;
import offersApp.repository.UserRepository;
import offersApp.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private OfferRepository offerRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private ReviewConverter reviewConverter;
    private EmailService emailService;

    @Autowired
    public ReviewServiceImpl(OfferRepository offerRepository, UserRepository userRepository, ReviewRepository reviewRepository, ReviewConverter reviewConverter, EmailService emailService) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
        this.emailService = emailService;
    }

    @Override
    public ReviewDto createAndNotify(ReviewDto reviewDto) {
       Offer offer = offerRepository.findById(reviewDto.getOfferId()).orElse(null);
       User customer = userRepository.findById(reviewDto.getUserId()).orElse(null);
       Review review = reviewConverter.fromDto(reviewDto, customer, offer);
       Review back = reviewRepository.save(review);
       reviewDto.setId(back.getId());
       notifyAgent(review);
       return reviewDto;
    }

    private String getReviewNotificationLink(Review review){
        return "http://localhost:8080/agent/offer/"+ review.getOffer().getId()+"/review/"+review.getId()+"/view";
    }

    private void notifyAgent(Review review){
        ReviewNotificationDto reviewNotificationDto = new ReviewNotificationDto(review.getOffer().getAgent().getUsername(), review.getOffer().getAgent().getEmail(), getReviewNotificationLink(review));
        emailService.sendReviewNotification(reviewNotificationDto);
    }

    @Override
    public void update(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getId()).orElse(null);
        review.setDate(reviewDto.getDate());
        review.setDescription(reviewDto.getDescription());
        review.setNoStars(reviewDto.getNoStars());
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        reviewRepository.delete(review);
    }

    @Override
    public ReviewDto findById(Long id) {
        return reviewConverter.toDto(reviewRepository.findById(id).orElse(null));
    }

    @Override
    public List<ReviewDto> findReviewsForOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElse(null);
        return reviewConverter.toDto(reviewRepository.findByOffer(offer));
    }
}
