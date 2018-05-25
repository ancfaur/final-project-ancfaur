package integration;

import offersApp.dto.OfferDto;
import offersApp.dto.UserDto;
import offersApp.dto.builder.OfferDtoBuilder;
import offersApp.entity.Role;
import offersApp.repository.RoleRepository;
import offersApp.service.offer.manage.OfferService;
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

import static offersApp.constants.ApplicationConstants.Categories.GROUP;
import static offersApp.constants.ApplicationConstants.Categories.PHYSICAL;
import static offersApp.constants.ApplicationConstants.Roles.*;


@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "offersApp.repository")
@ComponentScan({"offersApp.launcher", "offersApp.entity", "offersApp.dto", "offersApp.repository", "offersApp.service", "offersApp.controller", "offersApp.converter", "offersApp.config", "offersApp.constants"})
@EntityScan(basePackages ={"offersApp.entity"})
public class UserServiceIntegTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OfferService offerService;

    private final String EMAIL = "anc.faur@gmail.com";

    @Before
    public void setup(){
        roleRepository.deleteAll();
        userService.deleteAll();

        Role adminRole = new Role(ADMINISTRATOR);
        Role agentRole = new Role(AGENT);
        Role custRole = new Role(CUSTOMER);
        roleRepository.save(adminRole);
        roleRepository.save(agentRole);
        roleRepository.save(custRole);

    }

    @Test
    public void findAll(){
        int noBefore = userService.findAll().size();

        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setEmail(EMAIL);
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);
        userService.register(admin);

        UserDto agent = new UserDto();
        agent.setUsername("agent@yahoo.com");
        agent.setEmail(EMAIL);
        agent.setPassword("Password1#");
        agent.setRole(AGENT);
        userService.register(agent);

        UserDto customer = new UserDto();
        customer.setUsername("customer@yahoo.com");
        customer.setPassword("Password1#");
        customer.setEmail(EMAIL);
        customer.setRole(CUSTOMER);
        userService.register(customer);

        Assert.assertEquals(noBefore +3, userService.findAll().size());
    }

    @Test
    public void register(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back =userService.register(admin);
        UserDto registered= userService.findById(back.getId());
        Assert.assertEquals("administrator@yahoo.com", registered.getUsername());
        Assert.assertTrue(userService.checkPasswords("Password1#", registered.getPassword()));
    }

    @Test
    public void updateUser(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back= userService.register(admin);
        back.setUsername("changedUsername@yahoo.com");
        back.setPassword("ChangedPass#1");
        userService.update(back);
        UserDto updated =userService.findById(back.getId());
        Assert.assertEquals("changedUsername@yahoo.com", updated.getUsername());
        Assert.assertTrue(userService.checkPasswords("ChangedPass#1", updated.getPassword()));
    }

    @Test
    public void deleteAdministrator() {
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back= userService.register(admin);
        userService.delete(back.getId());
        Assert.assertNull(userService.findById(back.getId()));
    }

    @Test
    public void deleteAgentWithOffers() {
        UserDto agent = new UserDto();
        agent.setUsername("agent@yahoo.com");
        agent.setPassword("Password1#");
        agent.setRole(AGENT);
        UserDto backUserDto = userService.register(agent);

        List<String> categoryNames1 = new ArrayList<>();
        categoryNames1.add(GROUP);
        categoryNames1.add(PHYSICAL);

        OfferDto offerDto1 = new OfferDtoBuilder()
                .setName("sa fugiiim")
                .setPrice(200)
                .setInStock(20)
                .setLocation("Cimitirul de Masini")
                .setDescription("Impuscaturi colorate")
                .setAgent(backUserDto.getId())
                .setImage("pusca.jpg")
                .setNoPersons(10)
                .setDatePublished(new Date())
                .setCategories(categoryNames1)
                .setInitialNo(54)
                .setMinQuantity(2)
                .setPercentage(20)
                .build();
        OfferDto backOfferDto = offerService.create(offerDto1);

        userService.delete(backUserDto.getId());
        Assert.assertNull(userService.findById(backUserDto.getId()));
        Assert.assertNull(offerService.findById(backOfferDto.getId()));

    }

}