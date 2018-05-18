package offersApp.repository;

import offersApp.entity.Offer;
import offersApp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOffer(Offer offer);
}
