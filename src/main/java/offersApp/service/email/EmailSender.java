package offersApp.service.email;

import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.dto.email.SaleConfirmationDto;

public interface EmailSender {
    void configureAndSend(String subject, EmailTemplate emailTemplate, MailContentDto mailContentDto);
}
