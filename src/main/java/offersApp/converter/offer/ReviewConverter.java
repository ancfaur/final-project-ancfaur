package offersApp.converter.offer;

import offersApp.dto.ReviewDto;
import offersApp.entity.Offer;
import offersApp.entity.Review;
import offersApp.entity.User;

import java.util.List;

public interface ReviewConverter {
    Review fromDto(ReviewDto reviewDto, User customer, Offer offer);
    ReviewDto toDto (Review review);
    List<ReviewDto> toDto(List<Review> reviews);
}
