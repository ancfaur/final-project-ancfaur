package offersApp.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
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
    private String description;

    public Review() {
    }

    public Review(Long id, Offer offer, User user, Date date, int noStars, String description) {
        this.id = id;
        this.offer = offer;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Review)) {
            return false;
        }
        Review review = (Review) o;
        return review.getId().equals(this.id) && review.getOffer().equals(this.offer) && review.getUser().equals(this.user);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        return result;
    }
}
