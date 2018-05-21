package offersApp.dto.email;

public class ReviewNotificationDto {
    private String username;
    private String email;
    private String link;

    public ReviewNotificationDto(String username, String email, String link) {
        this.username = username;
        this.email = email;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
