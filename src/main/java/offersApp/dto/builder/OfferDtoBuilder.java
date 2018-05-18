package offersApp.dto.builder;

import offersApp.dto.OfferDto;

import java.util.Date;
import java.util.List;

public class OfferDtoBuilder {
    private OfferDto offerDto;

    public OfferDtoBuilder(){
        this.offerDto = new OfferDto();
    }

    public OfferDtoBuilder setId(Long id){
        offerDto.setId(id);
        return this;
    }

    public OfferDtoBuilder setName(String name){
        offerDto.setName(name);
        return this;
    }

    public OfferDtoBuilder setPrice(float price){
        offerDto.setPrice(price);
        return this;
    }

    public OfferDtoBuilder setNoPersons(int no){
        offerDto.setNoPersons(no);
        return this;
    }

    public OfferDtoBuilder setLocation(String location){
        offerDto.setLocation(location);
        return this;
    }

    public OfferDtoBuilder setDescription(String description){
        offerDto.setDescription(description);
        return this;
    }

    public OfferDtoBuilder setImage(String image){
        offerDto.setImage(image);
        return this;
    }

    public OfferDtoBuilder setInStock(int no){
        offerDto.setInStock(no);
        return this;
    }

    public OfferDtoBuilder setInitialNo(int no){
        offerDto.setInitialNo(no);
        return this;
    }

    public OfferDtoBuilder setAgent(Long agentId){
        offerDto.setAgentId(agentId);
        return this;
    }

    public OfferDtoBuilder setDiscount(Long discountId){
        offerDto.setDiscountId(discountId);
        return this;
    }

    public OfferDtoBuilder setDatePublished(Date datePublished){
        offerDto.setDatePublished(datePublished);
        return this;
    }

    public OfferDtoBuilder setCategories(List<String> categories){
        offerDto.setCategories(categories);
        return this;
    }

    public OfferDtoBuilder setMinQuantity(int minQuantity){
        offerDto.setMinQuantity(minQuantity);
        return this;
    }

    public OfferDtoBuilder setPercentage(int percentage){
        offerDto.setPercentPerOffer(percentage);
        return this;
    }


    public OfferDto build(){
        return this.offerDto;
    }

}
