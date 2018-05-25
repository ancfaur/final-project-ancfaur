package integration;

import offersApp.dto.CategoryDto;
import offersApp.dto.OfferDto;
import offersApp.dto.ReviewDto;
import offersApp.dto.UserDto;
import offersApp.dto.builder.OfferDtoBuilder;
import offersApp.entity.Role;
import offersApp.repository.RoleRepository;
import offersApp.service.category.CategoryService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.review.ReviewService;
import offersApp.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.*;
import static offersApp.constants.ApplicationConstants.Roles.AGENT;
import static offersApp.constants.ApplicationConstants.Roles.CUSTOMER;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "offersApp.repository")
@ComponentScan({"offersApp.launcher", "offersApp.entity", "offersApp.dto", "offersApp.repository", "offersApp.service", "offersApp.controller", "offersApp.converter", "offersApp.config", "offersApp.constants"})
@EntityScan(basePackages = {"offersApp.entity"})
public class ReviewServiceIntegTest {
    @Autowired
    private OfferService offerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ReviewService reviewService;

    private Long agentId;
    private Long customerId;
    private Long offerId;

    @Before
    public void setup() {
        offerService.deleteAll();
        categoryService.deleteAll();
        roleRepository.deleteAll();
        userService.deleteAll();

        for (String categoryName : CATEGORIES) {
            CategoryDto categoryDto = new CategoryDto(categoryName, 0);
            categoryService.create(categoryDto);
        }

        roleRepository.save(new Role(AGENT));
        roleRepository.save(new Role(CUSTOMER));

        UserDto agent1 = new UserDto();
        agent1.setUsername("agent1@yahoo.com");
        agent1.setPassword("Agent1#");
        agent1.setRole(AGENT);
        agent1.setEmail("anc.faur@gmail.com");
        agentId = userService.register(agent1).getId();

        UserDto customer = new UserDto();
        customer.setUsername("customer@yahoo.com");
        customer.setPassword("Password1#");
        customer.setEmail("anc.faur@gmail.com");
        customer.setRole(CUSTOMER);
        customerId = userService.register(customer).getId();

        List<String> categoryNames1 = new ArrayList<>();
        categoryNames1.add(GROUP);
        categoryNames1.add(PHYSICAL);
        OfferDto offerDto1 = new OfferDtoBuilder()
                .setName("Tras cu arma in echipe")
                .setPrice(200)
                .setInStock(20)
                .setLocation("Cimitirul de Masini")
                .setDescription("Impuscaturi colorate")
                .setAgent(agentId)
                .setImage("pusca.jpg")
                .setNoPersons(10)
                .setDatePublished(new Date())
                .setCategories(categoryNames1)
                .setInitialNo(54)
                .setMinQuantity(2)
                .setPercentage(20)
                .build();
        offerId = offerService.create(offerDto1).getId();
    }

    @Test
    public void createReview() {
        int before = reviewService.findAll().size();
        ReviewDto reviewDto1 = new ReviewDto(null, offerId, customerId, new Date(), 2, "A fost ok", "customer@yahoo.com", "Tras cu arma in echipe");
        ReviewDto backReviewDto = reviewService.create(reviewDto1);
        int after = reviewService.findAll().size();
        Assert.assertEquals(after, before + 1);
        Assert.assertNotNull(reviewService.findById(backReviewDto.getId()));
    }

    @Test
    public void updateReview() {
        ReviewDto reviewDto1 = new ReviewDto(null, offerId, customerId, new Date(), 2, "A fost ok", "customer@yahoo.com", "Tras cu arma in echipe");
        ReviewDto backReviewDto = reviewService.create(reviewDto1);
        backReviewDto.setNoStars(5);
        reviewService.update(backReviewDto);
        Assert.assertEquals(5, reviewService.findById(backReviewDto.getId()).getNoStars());
    }

    @Test
    public void deleteReview() {
        ReviewDto reviewDto1 = new ReviewDto(null, offerId, customerId, new Date(), 2, "A fost ok", "customer@yahoo.com", "Tras cu arma in echipe");
        ReviewDto backReviewDto = reviewService.create(reviewDto1);
        int before = reviewService.findAll().size();
        reviewService.delete(backReviewDto.getId());
        int after = reviewService.findAll().size();
        Assert.assertEquals(before - 1, after);
        Assert.assertNull(reviewService.findById(backReviewDto.getId()));
    }


}
