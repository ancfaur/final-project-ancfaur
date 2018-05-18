package offersApp.service.offer.search;

import offersApp.converter.offer.OfferConverter;
import offersApp.dto.OfferDto;
import offersApp.entity.Offer;
import offersApp.repository.CategoryRepository;
import offersApp.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.ALL;
import static offersApp.constants.ApplicationConstants.Ordering.DATE;

@Controller
public class OfferSearchImpl implements OfferSearchService {
    private OrderingDelegator orderingDelegator;
    private OfferRepository offerRepository;
    private CategoryRepository categoryRepository;
    private Ordering orderer;
    private OfferConverter offerConverter;

    @Autowired
    public OfferSearchImpl(OrderingDelegator orderingDelegator, OfferRepository offerRepository, CategoryRepository categoryRepository, OfferConverter offerConverter) {
        this.orderingDelegator = orderingDelegator;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
        this.offerConverter = offerConverter;
    }

    public List<OfferDto> searchBy(String category, String ordering){
        List<Offer> offers = considerCategory(category);
        List<Offer> sorted = considerOrdering(ordering, offers);
        return offerConverter.toDto(sorted);
    }

    private List<Offer>considerCategory(String category){
        if (category.equals(ALL)){
            return offerRepository.findAll();
        }
        return   offerRepository.findByCategory(categoryRepository.findByName(category));
    }

    private List<Offer> considerOrdering(String ordering, List<Offer> offers){
        if (ordering.equals(DATE)) {
            return offers;
        }
        orderer = orderingDelegator.selectOrdering(ordering);
        return orderer.order(offers);
    }
}
