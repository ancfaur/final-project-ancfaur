package offersApp.service.offer.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.Ordering.NO_STARS;
import static offersApp.constants.ApplicationConstants.Ordering.PERCENT_DISCOUNT;
import static offersApp.constants.ApplicationConstants.Ordering.PRICE;

@Component
public class OrderingDelegator {
    private OrderByPrice orderByPrice;
    private OrderByDiscount orderByDiscount;
    private OrderByStarsNo orderByStarsNo;

    @Autowired
    public OrderingDelegator(OrderByPrice orderByPrice, OrderByDiscount orderByDiscount, OrderByStarsNo orderByStarsNo) {
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
