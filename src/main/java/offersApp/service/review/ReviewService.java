package offersApp.service.review;

import offersApp.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto create(ReviewDto reviewDto);
    void update(ReviewDto reviewDto);
    void delete(Long id);
    ReviewDto findById(Long id);
    List<ReviewDto> findReviewsForOffer(Long offerId);
    void deleteAll();

    List<ReviewDto> findAll();
}
