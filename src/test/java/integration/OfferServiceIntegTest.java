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
public class OfferServiceIntegTest {
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
        UserDto agentBack = userService.register(agent1);
        agentId = agentBack.getId();
    }

    @Test
    public void deleteAll() {
        offerService.deleteAll();
        Assert.assertEquals(0, offerService.findAll().size());
    }

    @Test
    public void createOffer() {
        int before = offerService.findAll().size();
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
        OfferDto backOfferDto = offerService.create(offerDto1);
        int after = offerService.findAll().size();
        Assert.assertEquals(after, before + 1);
        Assert.assertNotNull(offerService.findById(backOfferDto.getId()));
    }

    @Test
    public void updateOffer() {
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
        OfferDto backOfferDto = offerService.create(offerDto1);
        backOfferDto.setPercentPerOffer(100);
        offerService.update(backOfferDto);
        OfferDto now = offerService.findById(backOfferDto.getId());
        Assert.assertEquals(100, now.getPercentPerOffer());
    }

    @Test
    public void deleteOfferWithReviews() {
        UserDto customer = new UserDto();
        customer.setUsername("customer@yahoo.com");
        customer.setPassword("Password1#");
        customer.setEmail("anc.faur@gmail.com");
        customer.setRole(CUSTOMER);
        UserDto userDtoBack = userService.register(customer);

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
        OfferDto backOfferDto = offerService.create(offerDto1);
        ReviewDto reviewDto1 = new ReviewDto(null, backOfferDto.getId(), userDtoBack.getId(), new Date(), 2, "A fost ok", "cust", "Tras cu arma in echipe");
        ReviewDto backReviewDto = reviewService.create(reviewDto1);

        offerService.delete(backOfferDto.getId());
        Assert.assertNull(offerService.findById(backOfferDto.getId()));
        Assert.assertNull(reviewService.findById(backReviewDto.getId()));
    }

}
