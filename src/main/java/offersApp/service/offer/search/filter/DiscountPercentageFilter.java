package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountPercentageFilter extends FilterDecorator {
    private float minPercentageExpected;

    public DiscountPercentageFilter(ListContainer listContainer, float minPercentageExpected) {
        super(listContainer);
        this.minPercentageExpected = minPercentageExpected;
    }

    public String getDescription(){
        return listContainer.getDescription() + "\n discount percentage at least " + minPercentageExpected;
    }

    @Override
    public List<Offer> getOffers() {
        return listContainer.getOffers()
                .stream()
                .filter(offer -> (offer.getDiscount().getPercentDiscountPerOffer()>=minPercentageExpected))
                .collect(Collectors.toList());
    }
}
