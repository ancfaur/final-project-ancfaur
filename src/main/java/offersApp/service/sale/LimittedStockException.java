package offersApp.service.sale;


public class LimittedStockException extends Exception {

    public LimittedStockException(String message){
        super(message);
    }
}