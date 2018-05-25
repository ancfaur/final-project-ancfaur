package offersApp.service.email;

import offersApp.constants.mailTemplates.EmailTemplate;
import offersApp.dto.OfferDto;
import offersApp.dto.email.MailContentDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.entity.Category;
import offersApp.entity.User;
import offersApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static offersApp.constants.ApplicationConstants.EmailSubjects.OFFER_NOTIFICATION_SUBJECT;

@Component
public class OfferAddedManager implements SpecificManager {
    private CategoryRepository categoryRepository;
    private EmailSender emailSender;
    private EmailTemplate notifyOfferTemplate;

    @Autowired
    public OfferAddedManager(CategoryRepository categoryRepository, EmailSender emailSender, @Qualifier("notifyOfferTemp") EmailTemplate notifyOfferTemplate) {
        this.categoryRepository = categoryRepository;
        this.emailSender = emailSender;
        this.notifyOfferTemplate = notifyOfferTemplate;
    }

    @Override
    public void manage(Object object) {
        OfferDto offerDto = (OfferDto) object;
        List<Category> categories = new ArrayList<>();
        for (String categoryName : offerDto.getCategories()) {
            categories.add(categoryRepository.findByName(categoryName));
        }

        Map<User, List<String>> userCategories = new HashMap<>();
        for (Category category : categories) {
            for (User customer : category.getSubscribers()) {
                if (userCategories.containsKey(customer)) {
                    List<String> itsCategories = userCategories.get(customer);
                    itsCategories.add(category.getName());
                } else {
                    List<String> newCategoriesList = new ArrayList<>();
                    newCategoriesList.add(category.getName());
                    userCategories.put(customer, newCategoriesList);
                }
            }
        }

        for (Map.Entry<User, List<String>> entry : userCategories.entrySet()) {
            MailContentDto offerNotificationDto = new OfferNotificationDto(entry.getKey().getUsername(), entry.getKey().getEmail(), offerDto.getName(), entry.getValue(), getLinkOffer(offerDto.getId()), getLinkUnsubcribe());
            emailSender.configureAndSend(OFFER_NOTIFICATION_SUBJECT, notifyOfferTemplate, offerNotificationDto);
        }
    }

    private String getLinkOffer(Long offerId){
        return "http://localhost:8080/customer/offer/"+offerId+"/reviews";
    }

    private String getLinkUnsubcribe(){
        return "http://localhost:8080/customer/manageCategories";
    }

}
