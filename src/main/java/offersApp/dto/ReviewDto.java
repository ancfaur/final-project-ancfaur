package offersApp.dto;

import org.hibernate.validator.constraints.Range;

import java.util.Date;

public class ReviewDto {
    private Long id;
    private Long offerId;
    private Long userId;
    private Date date;

    @Range(min=1, max=5, message="The number of stars must be between 1 and 5")
    private int noStars;

    private String description;
    private String customerUsername;
    private String offerName;

    public ReviewDto() {
    }

    public ReviewDto(Long id, Long offerId, Long userId, Date date, int noStars, String description, String customerUsername, String offerName) {
        this.id = id;
        this.offerId = offerId;
        this.userId = userId;
        this.date = date;
        this.noStars = noStars;
        this.description = description;
        this.customerUsername = customerUsername;
        this.offerName = offerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNoStars() {
        return noStars;
    }

    public void setNoStars(int noStars) {
        this.noStars = noStars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
}
