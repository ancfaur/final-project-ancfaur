package offersApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private int minQuantity;
    private int percentDiscountPerOffer;

    public Discount() {
    }

    public Discount(int minQuantity, int percentDiscountPerOffer) {
        this.minQuantity = minQuantity;
        this.percentDiscountPerOffer = percentDiscountPerOffer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getPercentDiscountPerOffer() {
        return percentDiscountPerOffer;
    }

    public void setPercentDiscountPerOffer(int percentDiscountPerOffer) {
        this.percentDiscountPerOffer = percentDiscountPerOffer;
    }
}
