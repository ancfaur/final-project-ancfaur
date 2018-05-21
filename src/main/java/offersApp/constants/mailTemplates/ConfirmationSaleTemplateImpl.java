package offersApp.constants.mailTemplates;

import offersApp.dto.email.SaleConfirmationDto;
import offersApp.service.email.MailNotCustomizedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ConfirmationSaleTemplateImpl implements EmailTemplate {
    public static final String HEADER = "Dear customer ";
    public static final String INTRODUCTION = "Thank you for your order. \n\nThe resume of your command is:\n ";
    public static final String ENDING = "Please present the sale id to the agent when requiring the service. Have a great experience.\n\n XOXO, \nbestOffers.com";
    public String username;
    public String offerName;
    public int noOffers;
    public float sum;
    public Long saleId;
    private boolean withDiscount;
    private int minQuantityForDiscount;
    private int percentagePerOffer;

    public ConfirmationSaleTemplateImpl() {}

    @Override
    public void customizeMessage(Object o) {
        SaleConfirmationDto saleConfirmationDto = (SaleConfirmationDto) o;
        this.username = saleConfirmationDto.getUsername();
        this.offerName = saleConfirmationDto.getOfferName();
        this.noOffers = saleConfirmationDto.getNoPurchased();
        this.sum = saleConfirmationDto.getSum();
        this.saleId = saleConfirmationDto.getSaleId();
        this.minQuantityForDiscount = saleConfirmationDto.getMinQuantityForDiscount();
        this.percentagePerOffer = saleConfirmationDto.getPercentagePerOffer();
        this.withDiscount = saleConfirmationDto.isWithDiscount();
    }

    private String getDiscountResume(){
        if (withDiscount==true) return "As you have purchased more than "+ minQuantityForDiscount + " you have earn a discount of " + percentagePerOffer + " per offer.\n";
        return "Quick reminder: If you buy at least  "+ minQuantityForDiscount + " offers of this kind, you earn a discount of " + percentagePerOffer + " per offer.\n";
    }

    private String getOfferResume(){
        return "Offer: "+ offerName +"\n"+
                "Number: " + noOffers +"\n"+
                "Sum paid: " + sum + "\n"+
                "Sale id:" + saleId + "\n";

    }

    public String getContent() throws MailNotCustomizedException {
        if (username == null) throw new MailNotCustomizedException();
        return HEADER + username+"," + "\n" + INTRODUCTION + getOfferResume() + getDiscountResume()+ "\n"  + ENDING;
    }

}
