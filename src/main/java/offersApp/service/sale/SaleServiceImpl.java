package offersApp.service.sale;
import offersApp.converter.sale.SaleConverter;
import offersApp.dto.SaleDto;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.Sale;
import offersApp.entity.User;
import offersApp.repository.OfferRepository;
import offersApp.repository.SaleRepository;
import offersApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    private UserRepository userRepository;
    private OfferRepository offerRepository;
    private SaleRepository saleRepository;
    private SaleConverter saleConverter;

    @Autowired
    public SaleServiceImpl(UserRepository userRepository, OfferRepository offerRepository, SaleRepository saleRepository, SaleConverter saleConverter) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.saleRepository = saleRepository;
        this.saleConverter = saleConverter;
    }

    public float sell(SaleDto saleDto) throws LimittedStockException {
        Offer offer = offerRepository.findById(saleDto.getOfferId()).orElse(null);
        if (offer.getInStock() <saleDto.getQuantity()){
            throw(new LimittedStockException("Limitted stock on offer " + offer.getName() +"\n"
                    + "in stock = "+ offer.getInStock()+"\n"
                    + "required ="+saleDto.getQuantity()+"\n"
            ));
        }else{
            User customer = userRepository.findById(saleDto.getCustomerId()).orElse(null);
            float sumConsideringDiscount = evaluateDiscount(saleDto.getQuantity(), offer.getPrice(), offer.getDiscount());

            Sale sale = saleConverter.fromDto(saleDto, customer, offer, sumConsideringDiscount);
            saleRepository.save(sale);

            offer.setInStock(offer.getInStock() - saleDto.getQuantity());
            offerRepository.save(offer);
            return sumConsideringDiscount;
        }
    }

    private float evaluateDiscount(int saleQuantity, float pricePerOffer, Discount discount) {
        if (saleQuantity < discount.getMinQuantity()){
            return pricePerOffer * saleQuantity;
        }
        return (pricePerOffer - (pricePerOffer * discount.getPercentDiscountPerOffer())/100)*saleQuantity;
    }

}
