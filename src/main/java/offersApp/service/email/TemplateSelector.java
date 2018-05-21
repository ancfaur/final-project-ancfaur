package offersApp.service.email;

import offersApp.constants.mailTemplates.EmailTemplate;

public interface TemplateSelector {
    EmailTemplate selectTemplate(String template);
}
