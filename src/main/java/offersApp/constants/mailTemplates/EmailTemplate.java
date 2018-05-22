package offersApp.constants.mailTemplates;

import offersApp.dto.email.MailContentDto;
import offersApp.service.email.MailNotCustomizedException;

public interface EmailTemplate {
    String getContent() throws MailNotCustomizedException;
    void customizeMessage(MailContentDto mailContentDto);
}
