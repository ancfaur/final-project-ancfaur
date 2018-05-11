package offersApp.service.review;

import offersApp.dto.ReviewDto;
import offersApp.entity.Offer;

public interface ReviewService {
    ReviewDto create(ReviewDto reviewDto);
    void update(ReviewDto reviewDto);
    void delete(Long id);
    ReviewDto findById(Long id);
}
