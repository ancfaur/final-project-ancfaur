package offersApp.converter.sale;

import offersApp.dto.SaleDto;
import offersApp.entity.Offer;
import offersApp.entity.Sale;
import offersApp.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleConverterImpl implements SaleConverter{

    @Override
    public SaleDto toDto(Sale sale) {
        if (sale==null) return null;
        SaleDto saleDto = new SaleDto(sale.getId(), sale.getCustomer().getId(), sale.getOffer().getId(), sale.getQuantity(), sale.getDate());
        return saleDto;

    }

    @Override
    public Sale fromDto(SaleDto saleDto, User customer, Offer offer, float priceConsideringDiscount) {
        if (saleDto==null) return null;
        Sale sale = new Sale(customer, offer, saleDto.getQuantity(), saleDto.getDate(), priceConsideringDiscount);
        return sale;
    }

    @Override
    public List<SaleDto> toDto(List<Sale> sales) {
        List<SaleDto> saleDtos = new ArrayList<>();
        for(Sale sale:sales){
            saleDtos.add(toDto(sale));
        }
        return saleDtos;
    }
}
