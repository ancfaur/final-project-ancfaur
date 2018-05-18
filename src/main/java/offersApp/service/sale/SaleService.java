package offersApp.service.sale;

import offersApp.dto.SaleDto;

public interface SaleService {
    float sellAndNotify(SaleDto saleDto) throws LimittedStockException;
}
