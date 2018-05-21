package offersApp.service.email;

import offersApp.dto.email.OfferNotificationDto;
import offersApp.dto.email.ReviewNotificationDto;
import offersApp.dto.email.SaleConfirmationDto;

public interface EmailService {
    void sendSaleConfirmation(SaleConfirmationDto saleConfirmationDto);
    void sendReviewNotification(ReviewNotificationDto reviewNotificationDto);
    void sendOfferNotification(OfferNotificationDto offerNotificationDto);
}
