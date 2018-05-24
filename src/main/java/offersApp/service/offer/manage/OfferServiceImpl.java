package offersApp.service.offer.manage;

import offersApp.converter.offer.OfferConverter;
import offersApp.dto.OfferDto;
import offersApp.entity.Category;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;
import offersApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private DiscountRepository discountRepository;
    private OfferConverter offerConverter;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ReviewRepository reviewRepository;



    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, DiscountRepository discountRepository, OfferConverter offerConverter, UserRepository userRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
        this.offerRepository = offerRepository;
        this.discountRepository = discountRepository;
        this.offerConverter = offerConverter;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
           }

    @Override
    public OfferDto create(OfferDto offerDto) {
        User agent = userRepository.findById(offerDto.getAgentId()).orElse(null);
        Discount discount = new Discount(offerDto.getMinQuantity(), offerDto.getPercentPerOffer());

        List<Category> categories = new ArrayList<>();

        for(String categoryName:offerDto.getCategories()){
            categories.add(categoryRepository.findByName(categoryName));
        }

        Offer offer = offerConverter.fromDto(offerDto, categories, agent, discount);
        Offer back =offerRepository.save(offer);
        offerDto.setId(back.getId());
        return offerDto;
    }

    @Override
    public void delete(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElse(null);
        reviewRepository.deleteByOffer(offer);
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

    @Override
    public void deleteAll() {
        reviewRepository.deleteAll();
        offerRepository.deleteAll();
    }
}
