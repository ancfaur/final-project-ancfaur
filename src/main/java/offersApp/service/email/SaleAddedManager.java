package offersApp.service.email;

import offersApp.constants.mailTemplates.ConfirmationSaleTemplateImpl;
import offersApp.dto.SaleDto;
import offersApp.dto.email.SaleConfirmationDto;
import offersApp.entity.Offer;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.EmailSubjects.SALE_CONFIRMATION_SUBJECT;

@Component
public class SaleAddedManager implements SpecificManager {
    private UserRepository userRepository;
    private OfferRepository offerRepository;
    private EmailSender emailSender;
    private ConfirmationSaleTemplateImpl confirmationSaleTemplate;

    @Autowired
    public SaleAddedManager(UserRepository userRepository, OfferRepository offerRepository, EmailSender emailSender, ConfirmationSaleTemplateImpl confirmationSaleTemplate) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.emailSender = emailSender;
        this.confirmationSaleTemplate = confirmationSaleTemplate;
    }

    @Override
    public void manage(Object object) {
        SaleDto saleDto = (SaleDto) object;
        User customer = userRepository.findById(saleDto.getCustomerId()).orElse(null);
        Offer offer = offerRepository.findById(saleDto.getOfferId()).orElse(null);
        SaleConfirmationDto saleConfirmationDto = new SaleConfirmationDto(customer.getUsername(),customer.getEmail(), offer.getName(), saleDto.getQuantity(), saleDto.isWithDiscount(), offer.getDiscount().getMinQuantity(), offer.getDiscount().getPercentDiscountPerOffer(), saleDto.getSumToPay(), saleDto.getId());
        emailSender.configureAndSend(SALE_CONFIRMATION_SUBJECT, confirmationSaleTemplate, saleConfirmationDto);
    }

}
