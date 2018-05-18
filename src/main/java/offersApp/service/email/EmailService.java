package offersApp.service.email;

import offersApp.dto.SaleConfirmationDto;

public interface EmailService {
    void sendSaleConfirmation(SaleConfirmationDto saleConfirmationDto);
}
