package offersApp.constants.mailTemplates;

import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.service.email.MailNotCustomizedException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyOfferTemplateImpl implements EmailTemplate {
    public static final String HEADER = "Dear customer ";
    public static final String INTRODUCTION = "A new offer has been added that might interest you.";
    public static final String ENDING = "Hope this was helpful for you! \n\n XOXO, \nbestOffers.com";
    public List<String> categoryNames;
    public String offerName;
    public String username;
    public String linkOffer;
    public String linkUnsubscribe;

    public NotifyOfferTemplateImpl(){}

    @Override
    public void customizeMessage(MailContentDto o) {
        OfferNotificationDto offerNotificationDto = (OfferNotificationDto) o;
        this.username = offerNotificationDto.getUsername();
        this.categoryNames = offerNotificationDto.getCategoryNames();
        this.offerName = offerNotificationDto.getOfferName();
        this.linkOffer = offerNotificationDto.getLinkOffer();
        this.linkUnsubscribe = offerNotificationDto.getLinkUnsubscribe();
    }

    public String getInvitation(){
        return  "If \"" + offerName + "\" sounds interesting to you, visit the folowing link:\n" + linkOffer+"\n";
    }

    public String getUnsubscribeMessage(){
       String message ="You received this email as you are subscribed to: ";
       for(String categoryName: categoryNames){
           message +="\n*"+ categoryName;
       }
       message+="\n\n If you want to unsubscribe of any of your chosen categories, follow the link: \n"+ linkUnsubscribe;
       return message;
    }

    public String getContent() throws MailNotCustomizedException {
        if (username == null) throw new MailNotCustomizedException();
        return HEADER + username+"," + "\n\n" + INTRODUCTION + getInvitation()+ ENDING +"\n\n\n"+getUnsubscribeMessage();
    }


}
