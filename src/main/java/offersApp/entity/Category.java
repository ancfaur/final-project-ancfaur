package offersApp.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "customer_category",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<User> subscribers;

    public Category() {
    }

    public Category(String name, List<User> subscribers) {
        this.name = name;
        this.subscribers = subscribers;
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

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(User subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(User subscriber){
        subscribers.remove(subscriber);
    }
}
