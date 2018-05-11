package offersApp.dto;

import java.util.Date;

public class SaleDto {
    private Long id;
    private Long customerId;
    private Long offerId;
    private int quantity;
    private Date date;

    public SaleDto() {
    }

    public SaleDto(Long customerId, Long offerId, int quantity, Date date) {
        this.customerId = customerId;
        this.offerId = offerId;
        this.quantity = quantity;
        this.date = date;
    }

    public SaleDto(Long id, Long customerId, Long offerId, int quantity, Date date) {
        this.id = id;
        this.customerId = customerId;
        this.offerId = offerId;
        this.quantity = quantity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
