package offersApp.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="customer_id")
    private User customer;

    @ManyToOne(optional = false)
    @JoinColumn(name="offer_id")
    private Offer offer;

    private int quantity;

    private Date date;

    private float sum;


    public Sale() {
    }

    public Sale(User customer, Offer offer, int quantity, Date date, float sum) {
        this.customer = customer;
        this.offer = offer;
        this.quantity = quantity;
        this.date = date;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
