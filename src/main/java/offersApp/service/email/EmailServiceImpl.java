package offersApp.service.email;

import offersApp.constants.mailTemplates.ConfirmationSaleTemplateImpl;
import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.dto.email.SaleConfirmationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    private EmailTemplate emailTemplate;
    private TemplateSelector templateSelector;
    private final String FROM_EMAIL = "anc.faur@gmail.com";



    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateSelector templateSelector) {
        this.mailSender = mailSender;
        this.templateSelector = templateSelector;
    }

    public void configureAndSend(String subject, String templateName, MailContentDto mailContentDto){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailContentDto.getEmail());
        msg.setFrom(FROM_EMAIL);
        msg.setSubject(subject);
        emailTemplate = templateSelector.selectTemplate(templateName);
        emailTemplate.customizeMessage(mailContentDto);
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
}

