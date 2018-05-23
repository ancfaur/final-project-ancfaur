package offersApp.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

public class OfferDto {
    private Long id;
    private Long agentId;
    private Long discountId;

    private List<String> categories;
    private String name;

    @Min(value = 0, message = "The price should be a positive integer")
    private float price;
    private int inStock;

    @Min(value = 1, message = "The initial quantity should be at least one")
    private int initialNo;
    private String location;
    private String description;
    private  String image;

    @Min(value = 1, message = "The initial quantity should be at least one")
    private int noPersons;
    private Date datePublished;

    @Min(value = 1, message = "The initial quantity should be at least one")
    private int minQuantity;

    @Range(min=1, max=100, message="The percentage must be between 1 and 100")
    private int percentPerOffer;

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getPercentPerOffer() {
        return percentPerOffer;
    }

    public void setPercentPerOffer(int percentPerOffer) {
        this.percentPerOffer = percentPerOffer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int noInStock) {
        this.inStock = noInStock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(int noPersons) {
        this.noPersons = noPersons;
    }

    public int getInitialNo() {
        return initialNo;
    }

    public void setInitialNo(int initialNo) {
        this.initialNo = initialNo;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }
}
