package offersApp.service.offer.search.filter;

import offersApp.entity.Offer;
import org.springframework.stereotype.Component;

import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.ALL;

@Component
public class FilterAssembler {

    public ListContainer considerAll(List<Offer> offers, String keyword, List<String>categories, float minPrice, float maxPrice, int noPersons, int discountQuantity, int discounPercentage){
        ListContainer inter = new ListContainerImpl(offers);
        if (!(keyword.equals("") || keyword.equals(null))) inter = new KeywordFilter(inter, keyword);
        if (!categories.contains(ALL)) inter = new CategoryFilter(inter, categories);
        if (minPrice!=0 && maxPrice!=0) inter = new PriceFilter(inter, minPrice, maxPrice);
        if (noPersons!=0) inter = new NoPersonsFilter(inter, noPersons);
        if (discountQuantity!=0) inter = new DiscountQuantityFilter(inter, discountQuantity);
        if (discounPercentage!=0) inter = new DiscountPercentageFilter(inter, discounPercentage);
        return inter;
    }
}
