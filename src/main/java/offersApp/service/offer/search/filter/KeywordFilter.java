package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordFilter extends FilterDecorator {
    private String keyword;

    public KeywordFilter(ListContainer listContainer, String keyword) {
        super(listContainer);
        this.keyword = keyword;
    }

    public String getDescription(){
        return listContainer.getDescription() + "\n contains keyword  " + keyword;
    }

    @Override
    public List<Offer> getOffers(){
        return  listContainer.getOffers()
                .stream()
                .filter(offer -> (offer.getName().toLowerCase().contains(keyword.toLowerCase())|| offer.getLocation().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }
}
