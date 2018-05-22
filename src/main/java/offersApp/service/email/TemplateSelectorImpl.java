package offersApp.service.email;

import offersApp.constants.mailTemplates.ConfirmationSaleTemplateImpl;
import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.constants.mailTemplates.NotifyOfferTemplateImpl;
import offersApp.constants.mailTemplates.NotifyReviewTemplateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static offersApp.constants.ApplicationConstants.EmailTemplates.*;

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
       if (template.equals(SALE_CONFIRMATION_TEMPLATE)) return confirmationSaleTemplate;
       if (template.equals(REVIEW_NOTIFICATION_TEMPLATE)) return  notifyReviewTemplate;
       if (template.equals(OFFER_NOTIFICATION_TEMPLATE)) return notifyOfferTemplate;
       return null;
    }
}
