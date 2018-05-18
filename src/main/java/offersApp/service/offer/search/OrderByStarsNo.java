package offersApp.service.offer.search;
import offersApp.entity.Offer;
import offersApp.entity.Review;
import offersApp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderByStarsNo implements Ordering {
    private ReviewRepository reviewRepository;

    @Autowired
    public OrderByStarsNo(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Offer> order(List<Offer> offers){
        TreeMap<Float, Offer> sorted = new TreeMap<>();
        List<Review> reviews;
        float avg=0;
        for(Offer offer: offers){
            reviews = reviewRepository.findByOffer(offer);
            avg=0;
            for(Review review:reviews){
                avg += review.getNoStars();
            }
            avg = avg / reviews.size();
            sorted.put(new Float(5-avg), offer);
        }
        Collection collection = sorted.values();
        return new ArrayList<>(collection);
    }

}
