package offersApp.service.offer.manage;
import offersApp.dto.OfferDto;

import java.util.List;

public interface OfferService {
    OfferDto createAndNotify(OfferDto offerDto);
    void delete(Long offerId);
    void update(OfferDto offerDto);
    OfferDto findById(Long offerId);
    List<OfferDto> findOffersForAgent(String username);
    List<OfferDto> findAll();
}
