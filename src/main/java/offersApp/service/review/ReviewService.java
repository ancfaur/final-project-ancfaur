package offersApp.service.review;

import offersApp.dto.ReviewDto;
import offersApp.entity.Offer;

import java.util.List;

public interface ReviewService {
    ReviewDto createAndNotify(ReviewDto reviewDto);
    void update(ReviewDto reviewDto);
    void delete(Long id);
    ReviewDto findById(Long id);
    List<ReviewDto> findReviewsForOffer(Long offerId);
}
