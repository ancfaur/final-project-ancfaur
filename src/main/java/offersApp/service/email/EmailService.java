package offersApp.service.email;

import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.dto.email.SaleConfirmationDto;

public interface EmailService {
    void configureAndSend(String subject, String templateName, MailContentDto mailContentDto);
}
