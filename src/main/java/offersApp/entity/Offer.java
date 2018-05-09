package offersApp.entity;

import javax.persistence.*;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // ads details
    private String name;
    private float price;
    private int noPersons;
    private String location;
    private String description;
    private byte[] image;

    // administrative details
    private int inStock;
    private int initialNo;

    @ManyToOne(optional = false)    // optinal=false =>agent is mandatory
    @JoinColumn(name="agent_id")
    private User agent;

    @OneToOne
    @JoinColumn(name="discount_id")
    private Discount discount;

    public Offer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(int noPersons) {
        this.noPersons = noPersons;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getInitialNo() {
        return initialNo;
    }

    public void setInitialNo(int initialNo) {
        this.initialNo = initialNo;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
