package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class PriceFilter extends FilterDecorator {
    private float minPrice;
    private float maxPrice;

    public PriceFilter(ListContainer listContainer, float minPrice, float maxPrice){
        super(listContainer);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getDescription(){
        return listContainer.getDescription() + "\n price between " +minPrice+" and "+maxPrice;
    }

    @Override
    public List<Offer>  getOffers(){
       return listContainer.getOffers()
               .stream()
               .filter(offer -> (offer.getPrice()>=minPrice))
               .filter(offer -> (offer.getPrice()<=maxPrice))
               .collect(Collectors.toList());
    }
}
