package offersApp.service.offer.search;

import offersApp.converter.offer.OfferConverter;
import offersApp.dto.OfferDto;
import offersApp.dto.SearchDto;
import offersApp.entity.Offer;
import offersApp.repository.OfferRepository;
import offersApp.service.offer.search.filter.FilterAssembler;
import offersApp.service.offer.search.filter.ListContainer;
import offersApp.service.offer.search.order.Ordering;
import offersApp.service.offer.search.order.OrderingDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static offersApp.constants.ApplicationConstants.Ordering.DATE;

@Controller
public class OfferSearchImpl implements OfferSearchService {
    private OrderingDelegator orderingDelegator;
    private OfferRepository offerRepository;
    private OfferConverter offerConverter;
    private FilterAssembler filterAssembler;

    @Autowired
    public OfferSearchImpl(OrderingDelegator orderingDelegator, OfferRepository offerRepository, OfferConverter offerConverter, FilterAssembler filterAssembler) {
        this.orderingDelegator = orderingDelegator;
        this.offerRepository = offerRepository;
        this.offerConverter = offerConverter;
        this.filterAssembler = filterAssembler;
    }

    @Override
    public List<OfferDto> searchBy(SearchDto searchDto){
        ListContainer listContainer = filterAssembler.considerAll(offerRepository.findAll(), searchDto);
        List<Offer> filteredOffers = listContainer.getOffers();
        if (searchDto.getOrdering().equals(DATE)) {
            return offerConverter.toDto(filteredOffers);
        }
        Ordering orderer = orderingDelegator.selectOrdering(searchDto.getOrdering());
        return offerConverter.toDto(orderer.order(filteredOffers));
    }


    public String searchDescription(SearchDto searchDto){
        ListContainer listContainer = filterAssembler.considerAll(offerRepository.findAll(), searchDto);
        return listContainer.getDescription()+ " ORDERED BY " + searchDto.getOrdering();
    }
}
