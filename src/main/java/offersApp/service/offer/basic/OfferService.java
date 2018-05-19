package offersApp.service.offer.basic;
import offersApp.dto.OfferDto;
import offersApp.dto.ReviewDto;

import java.util.List;

public interface OfferService {
    OfferDto create(OfferDto offerDto);
    void delete(Long offerId);
    void update(OfferDto offerDto);
    OfferDto findById(Long offerId);
    List<OfferDto> findOffersForAgent(String username);
    List<OfferDto> findAll();
}
