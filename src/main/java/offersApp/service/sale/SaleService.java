package offersApp.service.sale;

import offersApp.dto.SaleDto;

public interface SaleService {
    float sell(SaleDto saleDto) throws LimittedStockException;
}
