package offersApp.dto;

import java.util.List;

public class SearchDto {
    private String keyword;
    private List<String> categories;
    private float minPrice;
    private float maxPrice;
    private int noPersons;
    private int discountQuantity;
    private int discountPercentage;
    private String ordering;

    public SearchDto() {
        keyword = null;
        categories = null;
        minPrice =0;
        maxPrice = 0;
        noPersons =0;
        discountQuantity = 0;
        discountPercentage =0;
        ordering = null;
    }

    public SearchDto(String keyword, List<String> categories, float minPrice, float maxPrice, int noPersons, int discountQuantity, int discountPercentage, String ordering) {
        this.keyword = keyword;
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.noPersons = noPersons;
        this.discountQuantity = discountQuantity;
        this.discountPercentage = discountPercentage;
        this.ordering = ordering;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(int noPersons) {
        this.noPersons = noPersons;
    }

    public int getDiscountQuantity() {
        return discountQuantity;
    }

    public void setDiscountQuantity(int discountQuantity) {
        this.discountQuantity = discountQuantity;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }
}
