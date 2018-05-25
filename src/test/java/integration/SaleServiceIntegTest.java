package integration;

import offersApp.dto.CategoryDto;
import offersApp.dto.OfferDto;
import offersApp.dto.SaleDto;
import offersApp.dto.UserDto;
import offersApp.dto.builder.OfferDtoBuilder;
import offersApp.entity.Role;
import offersApp.repository.RoleRepository;
import offersApp.service.category.CategoryService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.review.ReviewService;
import offersApp.service.sale.LimittedStockException;
import offersApp.service.sale.SaleService;
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
@EntityScan(basePackages ={"offersApp.entity"})
public class SaleServiceIntegTest {
    @Autowired
    private SaleService saleService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoleRepository roleRepository;


    private SaleDto saleDto;
    private static final int INITIAL_QUANTITY=100;
    private static final float OFFER_PRICE = 200.5F;
    private static final int STOCK_QUANTITY=100;
    private static final int POSSIBLE_QUANTITY =50;
    private static final int UNDERSTOCK_QUANTITY = 200;
    private static final int DISCOUNT_QUANTITY =5;
    private static final int DISCOUNT_PERCENTAGE =10;
    private static final int QUANTITY_NO_DISCOUNT =2;
    private static final int QUANTITY_WITH_DISCOUNT=7;
    private Long OFFER_ID;

    @Before
    public void setup(){
        offerService.deleteAll();
        categoryService.deleteAll();
        roleRepository.deleteAll();
        userService.deleteAll();

        roleRepository.save(new Role(AGENT));
        roleRepository.save(new Role(CUSTOMER));

        UserDto agent1 = new UserDto();
        agent1.setUsername("agent1@yahoo.com");
        agent1.setPassword("Agent1#");
        agent1.setRole(AGENT);
        agent1.setEmail("anc.faur@gmail.com");
        UserDto agentBack =userService.register(agent1);

        UserDto customer1 = new UserDto();
        customer1.setUsername("customer1@yahoo.com");
        customer1.setPassword("Customer1#");
        customer1.setRole(CUSTOMER);
        customer1.setEmail("anc.faur@gmail.com");
        UserDto custBack =userService.register(customer1);


        for(String categoryName: CATEGORIES){
            CategoryDto categoryDto = new CategoryDto(categoryName, 0);
            categoryService.create(categoryDto);
        }

        List<String> categoryNames1 = new ArrayList<>();
        categoryNames1.add(ROMANTIC);
        categoryNames1.add(PHYSICAL);

        OfferDto offerDto1 =new OfferDtoBuilder()
                .setName("oferta1")
                .setPrice(OFFER_PRICE)
                .setInStock(STOCK_QUANTITY)
                .setLocation("Padurea Baciu")
                .setDescription("E fain sa calaresti cu noi")
                .setAgent(agentBack.getId())
                .setImage("calut.jpg")
                .setNoPersons(1)
                .setDatePublished(new Date())
                .setCategories(categoryNames1)
                .setInitialNo(INITIAL_QUANTITY)
                .setMinQuantity(DISCOUNT_QUANTITY)
                .setPercentage(DISCOUNT_PERCENTAGE)
                .build();

        OfferDto offerBack =offerService.create(offerDto1);
        OFFER_ID = offerBack.getId();
        saleDto = new SaleDto(custBack.getId(), OFFER_ID, 0, new Date());
    }

    @Test
    public void saleWithLimittedStockException(){
        saleDto.setQuantity(UNDERSTOCK_QUANTITY);
        try {
            saleService.sell(saleDto);
            assert false;
        } catch (LimittedStockException e) {
            Assert.assertEquals(INITIAL_QUANTITY, offerService.findById(OFFER_ID).getInStock());
            assert true;
        }
    }


    @Test
    public void saleWithoutLimittedStockException(){
        saleDto.setQuantity(POSSIBLE_QUANTITY);

        try {
            saleService.sell(saleDto);
            Assert.assertEquals(INITIAL_QUANTITY - POSSIBLE_QUANTITY, offerService.findById(OFFER_ID).getInStock());
        } catch (LimittedStockException e) {
            assert false;
        }
    }

    @Test
    public void saleWithDiscount(){
        saleDto.setQuantity(QUANTITY_WITH_DISCOUNT);
        try {
            SaleDto saleDtoBack =saleService.sell(saleDto);
            Assert.assertEquals((OFFER_PRICE* QUANTITY_WITH_DISCOUNT)-(OFFER_PRICE * QUANTITY_WITH_DISCOUNT* DISCOUNT_PERCENTAGE/100), saleDtoBack.getSumToPay(), 0.0002);
        } catch (LimittedStockException e) {
            assert false;
        }
    }

    @Test
    public void saleWithNoDiscount(){
        saleDto.setQuantity(QUANTITY_NO_DISCOUNT);
        try {
            SaleDto saleDtoBack =saleService.sell(saleDto);
            Assert.assertEquals(OFFER_PRICE* QUANTITY_NO_DISCOUNT, saleDtoBack.getSumToPay(), 0.0002);
        } catch (LimittedStockException e) {
            assert false;
        }
    }
}