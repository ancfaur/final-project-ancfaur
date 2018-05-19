package offersApp.service.sale;

import offersApp.converter.sale.SaleConverter;
import offersApp.dto.SaleConfirmationDto;
import offersApp.dto.SaleDto;
import offersApp.entity.Offer;
import offersApp.entity.Sale;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.SaleRepository;
import offersApp.repository.UserRepository;
import offersApp.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    private UserRepository userRepository;
    private OfferRepository offerRepository;
    private SaleRepository saleRepository;
    private SaleConverter saleConverter;
    private EmailService emailService;

    @Autowired
    public SaleServiceImpl(UserRepository userRepository, OfferRepository offerRepository, SaleRepository saleRepository, SaleConverter saleConverter, EmailService emailService) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.saleRepository = saleRepository;
        this.saleConverter = saleConverter;
        this.emailService = emailService;
    }

    public float sellAndNotify(SaleDto saleDto) throws LimittedStockException {
        Offer offer = offerRepository.findById(saleDto.getOfferId()).orElse(null);
        checkOfferInStock(offer.getInStock(), saleDto.getQuantity());
        User customer = userRepository.findById(saleDto.getCustomerId()).orElse(null);
        boolean withDiscount = false;
        if (saleDto.getQuantity() >= offer.getDiscount().getMinQuantity()) {
            withDiscount = true;
        }

        float sum = computeSumConsideringDiscount(withDiscount, saleDto.getQuantity(), offer.getPrice(), offer.getDiscount().getPercentDiscountPerOffer());

        Sale sale = saleConverter.fromDto(saleDto, customer, offer, sum);
        Sale back = saleRepository.save(sale);

        offer.setInStock(offer.getInStock() - saleDto.getQuantity());
        offerRepository.save(offer);

        notifyCustomer(customer.getUsername(), customer.getEmail(), offer.getName(), saleDto.getQuantity(), withDiscount, offer.getDiscount().getMinQuantity(),
                offer.getDiscount().getPercentDiscountPerOffer(), sum, back.getId());
        return sum;
    }

    private void notifyCustomer(String username, String email, String name, int quantity, boolean withDiscount, int minQuantity, int percentDiscountPerOffer, float sum, Long id) {
        SaleConfirmationDto saleConfirmationDto = new SaleConfirmationDto(username, email, name, quantity, withDiscount, minQuantity, percentDiscountPerOffer, sum, id);
        emailService.sendSaleConfirmation(saleConfirmationDto);
    }

    private void checkOfferInStock(int inStock, int quantity) throws LimittedStockException {
        if (inStock < quantity) {
            throw (new LimittedStockException("Limitted stock on offer" + "\n"
                    + "in stock = " + inStock + "\n"
                    + "required =" + quantity + "\n"));
        }
    }

    private float computeSumConsideringDiscount(boolean withDiscount, int quantity, float pricePerOffer, int percentage) {
        if (withDiscount) {
            return (pricePerOffer - (pricePerOffer * percentage / 100)) * quantity;
        }
        return pricePerOffer * quantity;
    }

}
