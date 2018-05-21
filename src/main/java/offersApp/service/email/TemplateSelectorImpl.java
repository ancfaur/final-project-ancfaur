package offersApp.service.email;

import offersApp.constants.mailTemplates.ConfirmationSaleTemplateImpl;
import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.constants.mailTemplates.NotifyOfferTemplateImpl;
import offersApp.constants.mailTemplates.NotifyReviewTemplateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static offersApp.constants.ApplicationConstants.EmailTemplates.OFFER_NOTIFICATION;
import static offersApp.constants.ApplicationConstants.EmailTemplates.REVIEW_NOTIFICATION;
import static offersApp.constants.ApplicationConstants.EmailTemplates.SALE_CONFIRMATION;

@Component
public class TemplateSelectorImpl implements TemplateSelector{
    private NotifyOfferTemplateImpl notifyOfferTemplate;
    private NotifyReviewTemplateImpl notifyReviewTemplate;
    private ConfirmationSaleTemplateImpl confirmationSaleTemplate;

    @Autowired
    public TemplateSelectorImpl(NotifyOfferTemplateImpl notifyOfferTemplate, NotifyReviewTemplateImpl notifyReviewTemplate, ConfirmationSaleTemplateImpl confirmationSaleTemplate) {
        this.notifyOfferTemplate = notifyOfferTemplate;
        this.notifyReviewTemplate = notifyReviewTemplate;
        this.confirmationSaleTemplate = confirmationSaleTemplate;
    }

    @Override
    public EmailTemplate selectTemplate(String template) {
       if (template.equals(SALE_CONFIRMATION)) return confirmationSaleTemplate;
       if (template.equals(REVIEW_NOTIFICATION)) return  notifyReviewTemplate;
       if (template.equals(OFFER_NOTIFICATION)) return notifyOfferTemplate;
       return null;
    }
}
