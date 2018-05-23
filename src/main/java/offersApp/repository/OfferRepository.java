package offersApp.repository;

import offersApp.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import offersApp.entity.User;
import offersApp.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByAgent(User agent);
}