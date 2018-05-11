package offersApp.converter.sale;

import offersApp.dto.SaleDto;
import offersApp.entity.Offer;
import offersApp.entity.Sale;
import offersApp.entity.User;

import java.util.List;

public interface SaleConverter {
    SaleDto toDto(Sale sale);
    Sale fromDto(SaleDto saleDto, User customer, Offer offer, float priceConsideringDiscount);
    List<SaleDto> toDto(List<Sale> sales);
}
