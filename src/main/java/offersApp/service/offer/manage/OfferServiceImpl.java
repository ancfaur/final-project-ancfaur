package offersApp.service.offer.manage;

import offersApp.converter.offer.OfferConverter;
import offersApp.dto.OfferDto;
import offersApp.dto.email.OfferNotificationDto;
import offersApp.entity.Category;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;
import offersApp.repository.CategoryRepository;
import offersApp.repository.DiscountRepository;
import offersApp.repository.OfferRepository;
import offersApp.repository.UserRepository;
import offersApp.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private DiscountRepository discountRepository;
    private OfferConverter offerConverter;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private EmailService emailService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, DiscountRepository discountRepository, OfferConverter offerConverter, UserRepository userRepository, CategoryRepository categoryRepository, EmailService emailService) {
        this.offerRepository = offerRepository;
        this.discountRepository = discountRepository;
        this.offerConverter = offerConverter;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.emailService = emailService;
    }

    @Override
    public OfferDto createAndNotify(OfferDto offerDto) {
        User agent = userRepository.findById(offerDto.getAgentId()).orElse(null);
        Discount discount = new Discount(offerDto.getMinQuantity(), offerDto.getPercentPerOffer());

        List<Category> categories = new ArrayList<>();

        for(String categoryName:offerDto.getCategories()){
            categories.add(categoryRepository.findByName(categoryName));
        }

        Offer offer = offerConverter.fromDto(offerDto, categories, agent, discount);
        Offer back =offerRepository.save(offer);
        offerDto.setId(back.getId());

        notifyUsersOfCategories(categories, back);
        return offerDto;
    }

    private void notifyUsersOfCategories(List<Category> categories, Offer offer) {
        Map<User, List<String>> userCategories = new HashMap<>();
        for(Category category:categories){
            for(User customer: category.getSubscribers()){
                if (userCategories.containsKey(customer)){
                    List<String> itsCategories = userCategories.get(customer);
                    itsCategories.add(category.getName());
                }else{
                    List<String> newCategoriesList = new ArrayList<>();
                    newCategoriesList.add(category.getName());
                    userCategories.put(customer, newCategoriesList);
                }
            }
        }

        for (Map.Entry<User, List<String>> entry : userCategories.entrySet()) {
            OfferNotificationDto offerNotificationDto = new OfferNotificationDto(entry.getKey().getUsername(), entry.getKey().getEmail(), offer.getName(), entry.getValue(), getLinkOffer(offer.getId()), getLinkUnsubcribe());
            emailService.sendOfferNotification(offerNotificationDto);
        }
    }

    private String getLinkOffer(Long offerId){
     return "http://localhost:8080/customer/offer/"+offerId+"/reviews";
    }

    private String getLinkUnsubcribe(){
        return "http://localhost:8080/customer/manageCategories";
    }

    @Override
    public void delete(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElse(null);
        offerRepository.delete(offer);
    }

    @Override
    public void update(OfferDto offerDto) {
        User agent = userRepository.findById(offerDto.getAgentId()).orElse(null);
        Discount discount = new Discount(offerDto.getMinQuantity(), offerDto.getPercentPerOffer());
        List<Category> categories = new ArrayList<>();
        for(String categoryName:offerDto.getCategories()){
            categories.add(categoryRepository.findByName(categoryName));
        }
        Offer offer = offerConverter.fromDto(offerDto, categories, agent, discount);
        Offer back =offerRepository.save(offer);
        offerDto.setId(back.getId());
    }

    @Override
    public OfferDto findById(Long offerId) {
        return offerConverter.toDto(offerRepository.findById(offerId).orElse(null));

    }

    @Override
    public List<OfferDto> findOffersForAgent(String username) {
        User agent = userRepository.findByUsername(username);
        List<Offer> offers = offerRepository.findByAgent(agent);
        return offerConverter.toDto(offers);
    }

    @Override
    public List<OfferDto> findAll() {
       return offerConverter.toDto(offerRepository.findAll());
    }
}
