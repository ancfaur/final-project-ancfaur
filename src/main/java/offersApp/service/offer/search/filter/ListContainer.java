package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;

public interface ListContainer {
    String getDescription();
    List<Offer> getOffers();
}
