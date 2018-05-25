package offersApp.service.offer.search.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.Ordering.*;

@Component
public class OrderingDelegator {
    private Ordering orderByPrice;
    private Ordering orderByDiscount;
    private Ordering orderByStarsNo;

    @Autowired
    public OrderingDelegator(@Qualifier("orderByPrice") Ordering orderByPrice, @Qualifier("orderByDiscount") Ordering orderByDiscount, @Qualifier("orderByStarsNo") Ordering orderByStarsNo) {
        this.orderByPrice = orderByPrice;
        this.orderByDiscount = orderByDiscount;
        this.orderByStarsNo = orderByStarsNo;
    }

    public Ordering selectOrdering(String ordering){
        if (ordering.equals(PRICE)) return orderByPrice;
        if (ordering.equals(PERCENT_DISCOUNT)) return orderByDiscount;
        if (ordering.equals(NO_STARS)) return orderByStarsNo;
        return null;
    }
}
