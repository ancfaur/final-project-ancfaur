package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;

public class ListContainerImpl implements ListContainer {
    public List<Offer> offers;
    public String description = "filtered by:";

    public ListContainerImpl(List<Offer> offers) {
        this.offers = offers;
    }

    public String getDescription(){
        return description;
    }

    public List<Offer>getOffers(){
        return offers;
    }
}
