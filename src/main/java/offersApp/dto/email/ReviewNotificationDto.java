package offersApp.dto.email;

public class ReviewNotificationDto extends MailContentDto {
    private String link;

    public ReviewNotificationDto(String username, String email,  String link) {
        super(username, email);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
