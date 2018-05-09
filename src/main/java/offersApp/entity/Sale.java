package offersApp.entity;

import javax.persistence.*;

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

    public Sale(User customer, Offer offer, int quantity) {
        this.customer = customer;
        this.offer = offer;
        this.quantity = quantity;
    }

    public Sale() {
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
}
