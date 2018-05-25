package offersApp.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.EmailTemplates.*;

@Component
public class ManagerSelector {
    private SpecificManager saleAddedManager;
    private SpecificManager offerAddedManager;
    private SpecificManager reviewAddedManager;

    @Autowired
    public ManagerSelector(@Qualifier("saleAddedManager") SpecificManager saleAddedManager, @Qualifier("offerAddedManager") SpecificManager offerAddedManager, @Qualifier("reviewAddedManager") SpecificManager reviewAddedManager) {
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
