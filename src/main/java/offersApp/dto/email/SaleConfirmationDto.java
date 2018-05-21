package offersApp.dto.email;

public class SaleConfirmationDto {
    private String username;
    private String email;
    private String offerName;
    private int noPurchased;
    private boolean withDiscount;
    private int minQuantityForDiscount;
    private int percentagePerOffer;
    private float sum;
    private Long saleId;

    public SaleConfirmationDto(String username, String email, String offerName, int noPurchased, boolean withDiscount, int minQuantityForDiscount, int percentagePerOffer, float sum, Long saleId) {
        this.username = username;
        this.email = email;
        this.offerName = offerName;
        this.noPurchased = noPurchased;
        this.withDiscount = withDiscount;
        this.minQuantityForDiscount = minQuantityForDiscount;
        this.percentagePerOffer = percentagePerOffer;
        this.sum = sum;
        this.saleId = saleId;
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

    public boolean isWithDiscount() {
        return withDiscount;
    }

    public void setWithDiscount(boolean withDiscount) {
        this.withDiscount = withDiscount;
    }

    public int getMinQuantityForDiscount() {
        return minQuantityForDiscount;
    }

    public void setMinQuantityForDiscount(int minQuantityForDiscount) {
        this.minQuantityForDiscount = minQuantityForDiscount;
    }

    public int getPercentagePerOffer() {
        return percentagePerOffer;
    }

    public void setPercentagePerOffer(int percentagePerOffer) {
        this.percentagePerOffer = percentagePerOffer;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public int getNoPurchased() {
        return noPurchased;
    }

    public void setNoPurchased(int noPurchased) {
        this.noPurchased = noPurchased;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }
}
