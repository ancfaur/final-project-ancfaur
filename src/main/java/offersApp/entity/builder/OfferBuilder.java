package offersApp.entity.builder;

import offersApp.entity.Category;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;

import java.util.Date;
import java.util.List;

public class OfferBuilder {
    private Offer offer;

    public OfferBuilder(){
        offer = new Offer();
    }

    public OfferBuilder setId(Long id){
        offer.setId(id);
        return this;
    }

    public OfferBuilder setName(String name){
        offer.setName(name);
        return this;
    }

    public OfferBuilder setPrice(float price){
        offer.setPrice(price);
        return this;
    }

    public OfferBuilder setNoPersons(int no){
        offer.setNoPersons(no);
        return this;
    }

    public OfferBuilder setLocation(String location){
        offer.setLocation(location);
        return this;
    }

    public OfferBuilder setDescription(String description){
        offer.setDescription(description);
        return this;
    }

    public OfferBuilder setImage(byte[] image){
        offer.setImage(image);
        return this;
    }

    public OfferBuilder setInStock(int no){
        offer.setInStock(no);
        return this;
    }

    public OfferBuilder setInitialNo(int no){
        offer.setInitialNo(no);
        return this;
    }

    public OfferBuilder setAgent(User agent){
        offer.setAgent(agent);
        return this;
    }

    public OfferBuilder setDiscount(Discount discount){
        offer.setDiscount(discount);
        return this;
    }

    public OfferBuilder setDatePublished(Date date){
        offer.setDatePublished(date);
        return this;
    }

    public OfferBuilder setCategories(List<Category> categories){
        offer.setCategories(categories);
        return this;
    }

    public Offer build(){
        return this.offer;
    }
}
