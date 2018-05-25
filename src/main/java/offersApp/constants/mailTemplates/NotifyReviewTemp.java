package offersApp.constants.mailTemplates;

import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.service.email.MailNotCustomizedException;
import org.springframework.stereotype.Component;

@Component
public class NotifyReviewTemp implements EmailTemplate {
    public static final String HEADER = "Dear agent ";
    public static final String INTRODUCTION = "A new review was added to your offer. \n\n The link below show your client's feedback for you.\n ";
    public static final String ENDING = "Hope this was helpful for you and your bussiness. \n\n XOXO, \nbestOffers.com";
    public String username;
    public String link;

    public NotifyReviewTemp() {
    }

    @Override
    public void customizeMessage(MailContentDto o) {
        ReviewNotificationDto reviewNotificationDto = (ReviewNotificationDto) o;
        this.username = reviewNotificationDto.getUsername();
        this.link = reviewNotificationDto.getLink();
    }


    public String getContent() throws MailNotCustomizedException {
        if (username == null) throw new MailNotCustomizedException();
        return HEADER + username+"," + "\n" + INTRODUCTION + link+ "\n"  + ENDING;
    }

}
