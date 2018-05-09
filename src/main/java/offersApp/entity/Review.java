package offersApp.entity;

import javax.persistence.*;
import java.util.Date;

public class Review {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)    // optinal=false =>offer is mandatory
    @JoinColumn(name="offer_id")
    private Offer offer;

    @ManyToOne(optional = false)    // optinal=false =>customer is mandatory
    @JoinColumn(name="customer_id")
    private User user;

    private Date date;
    private int noStars;

    public Review() {
    }

    public Review(Offer offer, User user, Date date, int noStars) {
        this.offer = offer;
        this.user = user;
        this.date = date;
        this.noStars = noStars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
