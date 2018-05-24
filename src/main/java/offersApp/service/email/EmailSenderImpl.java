package offersApp.service.email;

import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.email.MailContentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderImpl implements EmailSender {
    private JavaMailSender mailSender;
    private final String FROM_EMAIL = "anc.faur@gmail.com";



    @Autowired
    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void configureAndSend(String subject, EmailTemplate emailTemplate, MailContentDto mailContentDto){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailContentDto.getEmail());
        msg.setFrom(FROM_EMAIL);
        msg.setSubject(subject);
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

