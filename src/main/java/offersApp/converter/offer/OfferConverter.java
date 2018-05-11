package offersApp.converter.offer;

import offersApp.dto.OfferDto;
import offersApp.dto.UserDto;
import offersApp.entity.Category;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;

import java.util.List;

public interface OfferConverter {
    Offer fromDto(OfferDto offerDto, List<Category> categories, User agent, Discount discount);
    OfferDto toDto (Offer offer);
    List<OfferDto> toDto(List<Offer> offers);
}
