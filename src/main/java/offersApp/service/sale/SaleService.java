package offersApp.service.sale;

import offersApp.dto.SaleDto;

public interface SaleService {
   SaleDto sell(SaleDto saleDto) throws LimittedStockException;
}
