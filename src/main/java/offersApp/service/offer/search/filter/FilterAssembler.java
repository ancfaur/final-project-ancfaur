package offersApp.service.offer.search.filter;

import offersApp.dto.SearchDto;
import offersApp.entity.Offer;
import org.springframework.stereotype.Component;

import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.ALL;

@Component
public class FilterAssembler {

    public ListContainer considerAll(List<Offer> offers, SearchDto searchDto) {
        ListContainer inter = new ListContainerImpl(offers);
        if (!(searchDto.getKeyword().equals("") || searchDto.getKeyword().equals(null)))
            inter = new KeywordFilter(inter, searchDto.getKeyword());
        if (!searchDto.getCategories().contains(ALL)) inter = new CategoryFilter(inter, searchDto.getCategories());
        if (searchDto.getMinPrice() != 0 && searchDto.getMaxPrice() != 0)
            inter = new PriceFilter(inter, searchDto.getMinPrice(), searchDto.getMaxPrice());
        if (searchDto.getNoPersons() != 0) inter = new NoPersonsFilter(inter, searchDto.getNoPersons());
        if (searchDto.getDiscountQuantity() != 0)
            inter = new DiscountQuantityFilter(inter, searchDto.getDiscountQuantity());
        if (searchDto.getDiscountPercentage() != 0)
            inter = new DiscountPercentageFilter(inter, searchDto.getDiscountPercentage());
        return inter;
    }
}
