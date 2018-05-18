package offersApp.service.email;

import offersApp.constants.MailNotCustomizedException;
import offersApp.constants.ConfirmationSaleTemplate;
import offersApp.dto.SaleConfirmationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    private ConfirmationSaleTemplate confirmationSaleTemplate;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, ConfirmationSaleTemplate confirmationSaleTemplate) {
        this.mailSender = mailSender;
        this.confirmationSaleTemplate = confirmationSaleTemplate;
    }

    @Override
    public void sendSaleConfirmation(SaleConfirmationDto saleConfirmationDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(saleConfirmationDto.getEmail());
        msg.setFrom("anc.faur@gmail.com");
        msg.setSubject("offer confirmation");
        confirmationSaleTemplate.customizeMessage(saleConfirmationDto);
        try {
            msg.setText(confirmationSaleTemplate.getContent());
        } catch (MailNotCustomizedException e) {
            e.printStackTrace();
        }

        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println("**********************"+ex.getMessage());
        }
    }
}

