package offersApp.bootstrap;

import offersApp.dto.*;
import offersApp.dto.builder.OfferDtoBuilder;
import offersApp.entity.Role;
import offersApp.repository.RoleRepository;
import offersApp.service.category.CategoryService;
import offersApp.service.email.EmailService;
import offersApp.service.offer.manage.OfferService;
import offersApp.service.review.ReviewService;
import offersApp.service.sale.LimittedStockException;
import offersApp.service.sale.SaleService;
import offersApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.*;
import static offersApp.constants.ApplicationConstants.EmailTemplates.*;
import static offersApp.constants.ApplicationConstants.Roles.*;

@Component
public class Bootstrap {
    private RoleRepository roleRepository;
    private UserService userService;
    private OfferService offerService;
    private CategoryService categoryService;
    private SaleService saleService;
    private ReviewService reviewService;
    private EmailService emailService;

    private final String OFFER1_NAME = "Caluti";
    private final String OFFER2_NAME = "Weekend Paris";
    private final String OFFER3_NAME = "Targ de mancaruri traditionale";
    private final String CUSTOMER1_USERNAME = "customer1@yahoo.com";
    private final String CUSTOMER2_USERNAME = "customer2@yahoo.com";


    @Autowired
    public Bootstrap(RoleRepository roleRepository, UserService userService, OfferService offerService, CategoryService categoryService, SaleService saleService, ReviewService reviewService, EmailService emailService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.saleService = saleService;
        this.reviewService = reviewService;
        this.emailService = emailService;
    }

    @PostConstruct
    public void populateDb(){
        addRoles();
        addCategories();
        addAdmin();
        addAgents();
        addCustomers();

        addOffers();
        addSales();
        addReviews();

        addOfferAndNotify();
        addReviewAndNotify();
        addSaleAndNotify();


        // test update category
        // updateCategoryAfterAll();

    }

    private void addRoles(){
        Role adminRole = new Role(ADMINISTRATOR);
        Role customerRole = new Role(CUSTOMER);
        Role agentRole = new Role(AGENT);

        roleRepository.save(adminRole);
        roleRepository.save(customerRole);
        roleRepository.save(agentRole);
    }

    private void addCategories(){
     for(String categoryName: CATEGORIES){
         CategoryDto categoryDto = new CategoryDto(categoryName, 0);
         categoryService.create(categoryDto);
     }
    }

    private void addAdmin(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Administrator1#");
        admin.setRole(ADMINISTRATOR);
        admin.setEmail("anc.faur@gmail.com");
        userService.register(admin);
    }

    private void addAgents(){
        UserDto agent1 = new UserDto();
        agent1.setUsername("agent1@yahoo.com");
        agent1.setPassword("Agent1#");
        agent1.setRole(AGENT);
        agent1.setEmail("anc.faur@gmail.com");
        userService.register(agent1);

        UserDto agent2 = new UserDto();
        agent2.setUsername("agent2@yahoo.com");
        agent2.setPassword("Agent1#");
        agent2.setRole(AGENT);
        agent2.setEmail("anc.faur@gmail.com");
        userService.register(agent2);

    }

    private void addCustomers(){
        UserDto customer1 = new UserDto();
        customer1.setUsername(CUSTOMER1_USERNAME);
        customer1.setPassword("Customer1#");
        customer1.setRole(CUSTOMER);
        customer1.setEmail("anc.faur@gmail.com");
        UserDto back1 =userService.register(customer1);


        List<String> categoryNames1= new ArrayList<>();
        categoryNames1.add(CULINAR);
        categoryNames1.add(GROUP);
        categoryNames1.add(FAMILY);
        categoryNames1.add(CULTURAL);
        for (String categoryName: categoryNames1){
            categoryService.subscribeUser(categoryName, back1.getId());
        }


        UserDto customer2 = new UserDto();
        customer2.setUsername(CUSTOMER2_USERNAME);
        customer2.setPassword("Customer1#");
        customer2.setRole(CUSTOMER);
        customer2.setEmail("anc.faur@gmail.com");
        UserDto back2 =userService.register(customer2);

        List<String> categoryNames2= new ArrayList<>();
        categoryNames2.add(ROMANTIC);
        categoryNames2.add(NIGHTLIFE);
        for (String categoryName: categoryNames2){
            categoryService.subscribeUser(categoryName, back2.getId());
        }

    }

    private void addOffers() {
        List<String> categoryNames1 = new ArrayList<>();
        categoryNames1.add(ROMANTIC);
        categoryNames1.add(PHYSICAL);

        OfferDto offerDto1 =new OfferDtoBuilder()
                .setName(OFFER1_NAME)
                .setPrice(50)
                .setInStock(20)
                .setLocation("Padurea Baciu")
                .setDescription("E fain sa calaresti cu noi")
                .setAgent(2L)
                .setImage("calut.jpg")
                .setNoPersons(1)
                .setDatePublished(new Date())
                .setCategories(categoryNames1)
                .setInitialNo(40)
                .setMinQuantity(5)
                .setPercentage(20)
                .build();
        offerService.create(offerDto1);


        List<String> categoryNames2 = new ArrayList<>();
        categoryNames2.add(ROMANTIC);
        categoryNames2.add(ACCOMODATION);

        OfferDto offerDto2 =new OfferDtoBuilder()
                .setName(OFFER2_NAME)
                .setPrice(200)
                .setInStock(10)
                .setLocation("Chiar langa Turnul Eiffel")
                .setDescription("Hotel 4 stele, 3 nopti, in weekend")
                .setAgent(3L)
                .setImage("cameraHotel.jpg")
                .setNoPersons(2)
                .setDatePublished(new Date())
                .setCategories(categoryNames2)
                .setInitialNo(10)
                .setMinQuantity(2)
                .setPercentage(10)
                .build();
        OfferDto back =offerService.create(offerDto2);


        List<String> categoryNames3 = new ArrayList<>();
        categoryNames3.add(CULINAR);
        categoryNames3.add(CULTURAL);

        OfferDto offerDto3 =new OfferDtoBuilder()
                .setName(OFFER3_NAME)
                .setPrice(56)
                .setInStock(100)
                .setLocation("Observator")
                .setDescription("Ceva inedit")
                .setAgent(3L)
                .setImage("papa.jpg")
                .setNoPersons(2)
                .setDatePublished(new Date())
                .setCategories(categoryNames3)
                .setInitialNo(100)
                .setMinQuantity(5)
                .setPercentage(10)
                .build();
        offerService.create(offerDto3);

        // test delete
        // offerService.delete(back.getId());


        //test update
//        List<String> allCategories = back.getCategories();
//        allCategories.add(FAMILY);
//        back.setCategories(allCategories);
//        DiscountDto discountDto22 =  new DiscountDto(155, 20);
//
//        offerService.update(back, discountDto22);

    }

    private void addSales(){

        // alowed with no discount
        SaleDto saleDto1 = new SaleDto(4L, 1L, 1, new Date());
        try {
            System.out.print("*******Sale1 sum is ="+ saleService.sell(saleDto1) );
        } catch (LimittedStockException e) {
           System.out.print("*******Sale 1-> Should not occur");
        }

        // alowed with discount
        SaleDto saleDto2 = new SaleDto(4L, 1L, 5, new Date());
        try {
            System.out.print("*******Sale2 sum is ="+ saleService.sell(saleDto2) );
        } catch (LimittedStockException e) {
            System.out.print("*******Sale 2-> Should not occur");
        }

        // not allowed
        SaleDto saleDto3 = new SaleDto(4L, 1L, 100, new Date());
        try {
            saleService.sell(saleDto3);
            System.out.print("*******Sale3 ");
        } catch (LimittedStockException e) {
            System.out.print("*******Sale 3-> Limited stock is okay");
        }

    }

    private void addReviews(){
        ReviewDto reviewDto1 = new ReviewDto(null, 1L, 4L, new Date(), 2, "A fost ok", CUSTOMER1_USERNAME, OFFER1_NAME);
        ReviewDto reviewDto2 = new ReviewDto(null, 1L, 5L, new Date(), 1,"deloc Genial!", CUSTOMER2_USERNAME, OFFER1_NAME);
        ReviewDto reviewDto3 = new ReviewDto(null, 1L, 5L, new Date(), 1,"Penal! Mai bine mergeam la scoala",CUSTOMER2_USERNAME, OFFER1_NAME);
        ReviewDto reviewDto4 = new ReviewDto(null, 2L, 4L, new Date(), 4, "A fost chiar super",CUSTOMER1_USERNAME, OFFER2_NAME);
        ReviewDto reviewDto5 = new ReviewDto(null, 2L, 5L, new Date(), 5,"Genial!", CUSTOMER2_USERNAME, OFFER2_NAME);
        ReviewDto reviewDto6 = new ReviewDto(null, 3L, 5L, new Date(), 5,"minunat",CUSTOMER2_USERNAME, OFFER3_NAME);


        reviewService.create(reviewDto1);
        reviewService.create(reviewDto2);
        reviewService.create(reviewDto3);
        reviewService.create(reviewDto4);
        reviewService.create(reviewDto5);
        reviewService.create(reviewDto6);

    }

    private void addOfferAndNotify() {
        List<String> categoryNames1 = new ArrayList<>();
        categoryNames1.add(GROUP);
        categoryNames1.add(PHYSICAL);

        OfferDto offerDto1 =new OfferDtoBuilder()
                .setName("Tras cu arma in echipe")
                .setPrice(200)
                .setInStock(20)
                .setLocation("Cimitirul de Masini")
                .setDescription("Impuscaturi colorate")
                .setAgent(2L)
                .setImage("pusca.jpg")
                .setNoPersons(10)
                .setDatePublished(new Date())
                .setCategories(categoryNames1)
                .setInitialNo(54)
                .setMinQuantity(2)
                .setPercentage(20)
                .build();
        OfferDto backOfferDto =offerService.create(offerDto1);
        emailService.sendEmail(OFFER_NOTIFICATION_TYPE, backOfferDto);
    }

    private void addReviewAndNotify() {
        ReviewDto reviewDto1 = new ReviewDto(null, 1L, 4L, new Date(), 1, "Oribil tare!", CUSTOMER1_USERNAME, OFFER1_NAME);
        ReviewDto backReviewDto =reviewService.create(reviewDto1);
        emailService.sendEmail(REVIEW_NOTIFICATION_TYPE, backReviewDto);
    }

    private void addSaleAndNotify() {
        SaleDto saleDto1 = new SaleDto(4L, 1L, 1, new Date());
        try {
           SaleDto backSaleDto = saleService.sell(saleDto1);
           emailService.sendEmail(SALE_CONFIRMATION_TYPE, backSaleDto);
        } catch (LimittedStockException e) {
            System.out.print("*******Sale 1-> Should not occur");
        }
    }


}

