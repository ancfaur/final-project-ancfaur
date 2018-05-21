package offersApp.dto.email;

import java.util.List;

public class OfferNotificationDto {
    private String username;
    private String email;
    private String offerName;
    private List<String> categoryNames;
    private String linkOffer;
    private String linkUnsubscribe;

    public OfferNotificationDto(String username, String email, String offerName, List<String> categoryNames, String linkOffer, String linkUnsubscribe) {
        this.username = username;
        this.email = email;
        this.offerName = offerName;
        this.categoryNames = categoryNames;
        this.linkOffer = linkOffer;
        this.linkUnsubscribe = linkUnsubscribe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getLinkOffer() {
        return linkOffer;
    }

    public void setLinkOffer(String linkOffer) {
        this.linkOffer = linkOffer;
    }

    public String getLinkUnsubscribe() {
        return linkUnsubscribe;
    }

    public void setLinkUnsubscribe(String linkUnsubscribe) {
        this.linkUnsubscribe = linkUnsubscribe;
    }
}
