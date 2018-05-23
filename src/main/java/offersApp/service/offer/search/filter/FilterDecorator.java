package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;

public abstract class FilterDecorator implements ListContainer {
    protected ListContainer listContainer;

    public FilterDecorator(ListContainer listContainer) {
        this.listContainer = listContainer;
    }

    @Override
    public abstract String getDescription();

    @Override
    public abstract List<Offer> getOffers();
}
