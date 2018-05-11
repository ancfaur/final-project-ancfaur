package offersApp.converter.offer;

import offersApp.dto.ReviewDto;
import offersApp.entity.Offer;
import offersApp.entity.Review;
import offersApp.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewConverterImpl implements ReviewConverter {
    @Override
    public Review fromDto(ReviewDto reviewDto, User customer, Offer offer) {
        Review review = new Review(offer, customer, reviewDto.getDate(), reviewDto.getNoStars(), reviewDto.getDescription());
        return review;
    }

    @Override
    public ReviewDto toDto(Review review) {
        ReviewDto reviewDto = new ReviewDto(review.getId(), review.getOffer().getId(), review.getUser().getId(), review.getDate(), review.getNoStars(), review.getDescription());
        return reviewDto;
    }

    @Override
    public List<ReviewDto> toDto(List<Review> reviews) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for(Review review:reviews){
            reviewDtos.add(toDto(review));
        }
        return reviewDtos;
    }
}
