package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountQuantityFilter extends FilterDecorator {
    private float maxQuantitySupported;

    public DiscountQuantityFilter(ListContainer listContainer, float maxQuantitySupported) {
       super(listContainer);
        this.maxQuantitySupported = maxQuantitySupported;
    }

    public String getDescription(){
        return listContainer.getDescription() + "\n quantity needed for discount at most " +maxQuantitySupported;
    }

    @Override
    public List<Offer> getOffers() {
        return  listContainer.getOffers()
                .stream()
                .filter(offer -> (offer.getDiscount().getMinQuantity()<=maxQuantitySupported))
                .collect(Collectors.toList());
    }
}
