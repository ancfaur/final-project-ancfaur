package offersApp.converter.offer;

import offersApp.dto.DiscountDto;
import offersApp.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverterImpl implements DiscountConverter {

    @Override
    public Discount fromDto(DiscountDto discountDto) {
        Discount discount = new Discount(discountDto.getMinQuanitity(), discountDto.getPercentPerOffer());
        return discount;
    }

    @Override
    public DiscountDto toDto(Discount discount) {
        DiscountDto discountDto = new DiscountDto(discount.getMinQuantity(), discount.getPercentDiscountPerOffer());
        return discountDto;
    }
}
