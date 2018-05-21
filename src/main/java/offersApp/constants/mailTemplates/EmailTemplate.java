package offersApp.constants.mailTemplates;

import offersApp.service.email.MailNotCustomizedException;

public interface EmailTemplate {
    String getContent() throws MailNotCustomizedException;
    void customizeMessage(Object o);
}
