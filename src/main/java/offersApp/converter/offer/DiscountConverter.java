package offersApp.converter.offer;

import offersApp.dto.DiscountDto;
import offersApp.entity.Discount;
import org.springframework.stereotype.Component;


public interface DiscountConverter {
    Discount fromDto(DiscountDto discountDto);
    DiscountDto toDto (Discount discount);
}
