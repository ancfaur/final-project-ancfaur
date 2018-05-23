package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class NoPersonsFilter extends FilterDecorator {
    private int actualNoPersons;

    public NoPersonsFilter(ListContainer listContainer, int actualNoPersons) {
        super(listContainer);
        this.actualNoPersons = actualNoPersons;
    }

    public String getDescription(){
        return listContainer.getDescription() + "\n no of persons allowed greater then " + actualNoPersons;
    }

    @Override
    public List<Offer> getOffers() {
        return listContainer.getOffers()
                .stream()
                .filter(offer -> (offer.getNoPersons()>= actualNoPersons))
                .collect(Collectors.toList());
    }
}
