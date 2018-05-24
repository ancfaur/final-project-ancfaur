package offersApp.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.EmailTemplates.OFFER_NOTIFICATION_TYPE;
import static offersApp.constants.ApplicationConstants.EmailTemplates.REVIEW_NOTIFICATION_TYPE;
import static offersApp.constants.ApplicationConstants.EmailTemplates.SALE_CONFIRMATION_TYPE;

@Component
public class ManagerSelector {
    private SaleAddedManager saleAddedManager;
    private OfferAddedManager offerAddedManager;
    private ReviewAddedManager reviewAddedManager;

    @Autowired
    public ManagerSelector(SaleAddedManager saleAddedManager, OfferAddedManager offerAddedManager, ReviewAddedManager reviewAddedManager) {
        this.saleAddedManager = saleAddedManager;
        this.offerAddedManager = offerAddedManager;
        this.reviewAddedManager = reviewAddedManager;
    }

    public SpecificManager selectManager(String mailType) {
        if (mailType.equals(SALE_CONFIRMATION_TYPE)) return saleAddedManager;
        if (mailType.equals(REVIEW_NOTIFICATION_TYPE)) return reviewAddedManager;
        if (mailType.equals(OFFER_NOTIFICATION_TYPE)) return offerAddedManager;
        return null;
    }
}
