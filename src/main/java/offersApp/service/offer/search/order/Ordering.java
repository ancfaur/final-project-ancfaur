package offersApp.service.offer.search.order;
import offersApp.dto.OfferDto;
import offersApp.entity.Offer;
import java.util.List;

public interface Ordering  {
    List<Offer> order(List<Offer>offers);
}
