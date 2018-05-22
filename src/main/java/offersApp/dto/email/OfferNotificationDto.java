package offersApp.dto.email;

import java.util.List;

public class OfferNotificationDto extends MailContentDto{
    private String offerName;
    private List<String> categoryNames;
    private String linkOffer;
    private String linkUnsubscribe;

    public OfferNotificationDto(String username, String email, String offerName, List<String> categoryNames, String linkOffer, String linkUnsubscribe) {
        super(username, email);
        this.offerName = offerName;
        this.categoryNames = categoryNames;
        this.linkOffer = linkOffer;
        this.linkUnsubscribe = linkUnsubscribe;
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
