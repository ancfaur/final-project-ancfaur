package offersApp.dto;

import java.util.Date;

public class ReviewDto {
    private Long id;
    private Long offerId;
    private Long userId;
    private Date date;
    private int noStars;
    private String description;

    public ReviewDto() {
    }

    public ReviewDto(Long offerId, Long userId, Date date, int noStars, String description) {
        this.offerId = offerId;
        this.userId = userId;
        this.date = date;
        this.noStars = noStars;
        this.description = description;
    }

    public ReviewDto(Long id, Long offerId, Long userId, Date date, int noStars, String description) {
        this.id = id;
        this.offerId = offerId;
        this.userId = userId;
        this.date = date;
        this.noStars = noStars;
        this.description = description;
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
}
