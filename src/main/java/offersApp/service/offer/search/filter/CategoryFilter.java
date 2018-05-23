package offersApp.service.offer.search.filter;
import offersApp.entity.Offer;
import java.util.ArrayList;
import java.util.List;


public class CategoryFilter extends FilterDecorator {
    private List<String> categories;

    public CategoryFilter(ListContainer listContainer, List<String> categories) {
        super(listContainer);
        this.categories = categories;
    }

    public String getDescription(){
        String description = listContainer.getDescription() + " \n category can be {";
        for(String category:categories){
            description = description + category + " ";
        }
        return description +"}";
    }

    @Override
    public List<Offer> getOffers() {
        List<Offer> offers = listContainer.getOffers();
        List<Offer> filtered = new ArrayList<>();

        for (Offer offer : offers) {
            for (String category : categories) {
                if (offer.belongsToCategory(category)) {
                    filtered.add(offer);
                    break;
                }
            }
        }

        return filtered;
    }
}
