package offersApp.service.email;

import offersApp.constants.mailTemplates.ConfirmationSaleTemplateImpl;
import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.dto.email.SaleConfirmationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import offersApp.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import offersApp.entity.User;

import static offersApp.constants.ApplicationConstants.EmailTemplates.OFFER_NOTIFICATION;
import static offersApp.constants.ApplicationConstants.EmailTemplates.REVIEW_NOTIFICATION;
import static offersApp.constants.ApplicationConstants.EmailTemplates.SALE_CONFIRMATION;

@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    private EmailTemplate emailTemplate;
    private TemplateSelector templateSelector;
    private final String REVIEW_NOTIFICATION_SUBJECT = "new review added";
    private final String OFFER_NOTIFICATION_SUBJECT = "new offer added";
    private final String SALE_CONFIRMATION_SUBJECT = "sale confirmation";
    private final String FROM_EMAIL = "anc.faur@gmail.com";



    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateSelector templateSelector) {
        this.mailSender = mailSender;
        this.templateSelector = templateSelector;
    }

    private void configureAndSend(String subject, String templateName, String email, Object customizeObj){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom(FROM_EMAIL);
        msg.setSubject(subject);
        emailTemplate = templateSelector.selectTemplate(templateName);
        emailTemplate.customizeMessage(customizeObj);
        try {
            msg.setText(emailTemplate.getContent());
        } catch (MailNotCustomizedException e) {
            e.printStackTrace();
        }
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendSaleConfirmation(SaleConfirmationDto saleConfirmationDto) {
       configureAndSend(SALE_CONFIRMATION_SUBJECT, SALE_CONFIRMATION, saleConfirmationDto.getEmail(), saleConfirmationDto);
    }

    @Override
    public void sendReviewNotification(ReviewNotificationDto reviewNotificationDto) {
        configureAndSend(REVIEW_NOTIFICATION_SUBJECT, REVIEW_NOTIFICATION, reviewNotificationDto.getEmail(), reviewNotificationDto);
    }

    public void sendOfferNotification(OfferNotificationDto offerNotificationDto){
        configureAndSend(OFFER_NOTIFICATION_SUBJECT, OFFER_NOTIFICATION, offerNotificationDto.getEmail(), offerNotificationDto);
    }

}

