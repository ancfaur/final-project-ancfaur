package offersApp.service.email;

import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.ReviewDto;
import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.entity.Offer;
import offersApp.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.EmailSubjects.REVIEW_NOTIFICATION_SUBJECT;

@Component
public class ReviewAddedManager implements SpecificManager {
    private OfferRepository offerRepository;
    private EmailSender emailSender;
    private EmailTemplate notifyReviewTemplate;

    @Autowired
    public ReviewAddedManager(OfferRepository offerRepository, EmailSender emailSender, @Qualifier("notifyReviewTemp") EmailTemplate notifyReviewTemplate) {
        this.offerRepository = offerRepository;
        this.emailSender = emailSender;
        this.notifyReviewTemplate = notifyReviewTemplate;
    }

    @Override
    public void manage(Object object) {
        ReviewDto reviewDto = (ReviewDto) object;
        Offer offer = offerRepository.findById(reviewDto.getOfferId()).orElse(null);
        MailContentDto reviewNotificationDto = new ReviewNotificationDto(offer.getAgent().getUsername(), offer.getAgent().getEmail(), getReviewNotificationLink(reviewDto));
        emailSender.configureAndSend(REVIEW_NOTIFICATION_SUBJECT, notifyReviewTemplate, reviewNotificationDto);
    }

    private String getReviewNotificationLink(ReviewDto reviewDto){
        return "http://localhost:8080/agent/offer/"+ reviewDto.getOfferId()+"/review/"+reviewDto.getId()+"/view";
    }


}
