package offersApp.service.sale;

import offersApp.converter.sale.SaleConverter;
import offersApp.dto.SaleDto;
import offersApp.entity.Offer;
import offersApp.entity.Sale;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.SaleRepository;
import offersApp.repository.UserRepository;
import offersApp.service.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SaleServiceImpl implements SaleService {
    private UserRepository userRepository;
    private OfferRepository offerRepository;
    private SaleRepository saleRepository;
    private SaleConverter saleConverter;
    private EmailSender emailService;

    @Autowired
    public SaleServiceImpl(UserRepository userRepository, OfferRepository offerRepository, SaleRepository saleRepository, SaleConverter saleConverter, EmailSender emailService) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.saleRepository = saleRepository;
        this.saleConverter = saleConverter;
        this.emailService = emailService;
    }

    public SaleDto sell(SaleDto saleDto) throws LimittedStockException {
        Offer offer = offerRepository.findById(saleDto.getOfferId()).orElse(null);
        checkOfferInStock(offer.getInStock(), saleDto.getQuantity());
        User customer = userRepository.findById(saleDto.getCustomerId()).orElse(null);
        boolean withDiscount = false;
        if (saleDto.getQuantity() >= offer.getDiscount().getMinQuantity()) {
            withDiscount = true;
        }

        float sum = computeSumConsideringDiscount(withDiscount, saleDto.getQuantity(), offer.getPrice(), offer.getDiscount().getPercentDiscountPerOffer());

        Sale sale = saleConverter.fromDto(saleDto, customer, offer, sum);
        Sale saleBack = saleRepository.save(sale);

        offer.setInStock(offer.getInStock() - saleDto.getQuantity());
        offerRepository.save(offer);
        saleDto.setId(saleBack.getId());
        saleDto.setSumToPay(sum);
        saleDto.setWithDiscount(withDiscount);
        return saleDto;
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
