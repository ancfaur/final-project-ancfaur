package offersApp.service.offer;
import offersApp.dto.DiscountDto;
import offersApp.dto.OfferDto;

import java.util.List;

public interface OfferService {
    OfferDto create(OfferDto offerDto, DiscountDto discountDto);
    void delete(Long offerId);
    void update(OfferDto offerDto, DiscountDto discountDto);
    OfferDto findById(Long offerId);
}
