package offersApp.dto;

public class DiscountDto {
    private int minQuanitity;
    private int percentPerOffer;

    public DiscountDto() {
    }

    public DiscountDto(int noOffers, int percentPerOffer) {
        this.minQuanitity = noOffers;
        this.percentPerOffer = percentPerOffer;
    }

    public int getMinQuanitity() {
        return minQuanitity;
    }

    public void setMinQuanitity(int noOffers) {
        this.minQuanitity = noOffers;
    }

    public int getPercentPerOffer() {
        return percentPerOffer;
    }

    public void setPercentPerOffer(int percentPerOffer) {
        this.percentPerOffer = percentPerOffer;
    }
}
