package offersApp.service.offer.search;

import offersApp.dto.OfferDto;

import java.util.List;

public interface OfferSearchService {
    List<OfferDto> searchBy(String category, String ordering);
}
