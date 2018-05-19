package offersApp.converter.offer;

import offersApp.dto.OfferDto;
import offersApp.dto.builder.OfferDtoBuilder;
import offersApp.entity.Category;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;
import offersApp.entity.builder.OfferBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OfferConverterImpl implements OfferConverter {

    @Override
    public Offer fromDto(OfferDto offerDto, List<Category> categories, User agent, Discount discount) {
        if (offerDto == null) return null;
        Offer offer = new OfferBuilder()
                .setId(offerDto.getId())
                .setName(offerDto.getName())
                .setPrice(offerDto.getPrice())
                .setInStock(offerDto.getInStock())
                .setLocation(offerDto.getLocation())
                .setDescription(offerDto.getDescription())
                .setAgent(agent)
                .setImage(offerDto.getImage())
                .setNoPersons(offerDto.getNoPersons())
                .setDiscount(discount)
                .setDatePublished(offerDto.getDatePublished())
                .setCategories(categories)
                .setInitialNo(offerDto.getInitialNo())
                .build();
        return offer;
    }

    @Override
    public OfferDto toDto(Offer offer) {
        if(offer==null) return null;
        List<String> categoriesName = new ArrayList<>();
        offer.getCategories().forEach(category-> categoriesName.add(category.getName()));

        OfferDto offerDto = new OfferDtoBuilder()
                .setId(offer.getId())
                .setName(offer.getName())
                .setPrice(offer.getPrice())
                .setInStock(offer.getInStock())
                .setLocation(offer.getLocation())
                .setDescription(offer.getDescription())
                .setAgent(offer.getAgent().getId())
                .setImage(offer.getImage())
                .setNoPersons(offer.getNoPersons())
                .setDiscount(offer.getDiscount().getId())
                .setDatePublished(offer.getDatePublished())
                .setCategories(categoriesName)
                .setInitialNo(offer.getInitialNo())
                .setMinQuantity(offer.getDiscount().getMinQuantity())
                .setPercentage(offer.getDiscount().getPercentDiscountPerOffer())
                .build();
        return offerDto;
    }

    @Override
    public List<OfferDto> toDto(List<Offer> offers) {
        List<OfferDto> offerDtos = new ArrayList<>();
        for(Offer offer:offers){
            offerDtos.add(toDto(offer));
        }
    return offerDtos;
    }
}
