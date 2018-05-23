package offersApp.service.offer.search;

import offersApp.dto.OfferDto;
import offersApp.dto.SearchDto;

import java.util.List;

public interface OfferSearchService {
    List<OfferDto> searchBy(SearchDto searchDto);
    String searchDescription(SearchDto searchDto);
}
