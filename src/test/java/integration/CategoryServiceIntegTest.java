package integration;

import offersApp.dto.CategoryDto;
import offersApp.dto.UserDto;
import offersApp.entity.Role;
import offersApp.repository.RoleRepository;
import offersApp.service.category.CategoryService;
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

import static offersApp.constants.ApplicationConstants.Categories.GROUP;
import static offersApp.constants.ApplicationConstants.Categories.ROMANTIC;
import static offersApp.constants.ApplicationConstants.Roles.CUSTOMER;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "offersApp.repository")
@ComponentScan({"offersApp.launcher", "offersApp.entity", "offersApp.dto", "offersApp.repository", "offersApp.service", "offersApp.controller", "offersApp.converter", "offersApp.config", "offersApp.constants"})
@EntityScan(basePackages = {"offersApp.entity"})
public class CategoryServiceIntegTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RoleRepository roleRepository;
    private Long customerId;

    @Before
    public void setup() {
        categoryService.deleteAll();
        roleRepository.deleteAll();
        userService.deleteAll();

        roleRepository.save(new Role(CUSTOMER));

        UserDto customer1 = new UserDto();
        customer1.setUsername("customer1@yahoo.com");
        customer1.setPassword("Customer1#");
        customer1.setRole(CUSTOMER);
        customer1.setEmail("anc.faur@gmail.com");
        customerId = userService.register(customer1).getId();
    }

    @Test
    public void createCategory() {
        int before = categoryService.findAll().size();
        CategoryDto categoryDto = new CategoryDto(GROUP, 0);
        CategoryDto back = categoryService.create(categoryDto);
        int after = categoryService.findAll().size();
        Assert.assertEquals(before + 1, after);
        Assert.assertNotNull(categoryService.findById(back.getId()));
    }

    @Test
    public void updateCategory() {
        CategoryDto categoryDto = new CategoryDto(GROUP, 0);
        CategoryDto back = categoryService.create(categoryDto);
        back.setName("group play");
        categoryService.update(back);
        Assert.assertTrue(categoryService.findById(back.getId()).getName().equals("group play"));
    }

    @Test
    public void deleteCategory() {
        CategoryDto categoryDto = new CategoryDto(GROUP, 0);
        CategoryDto back = categoryService.create(categoryDto);
        int before = categoryService.findAll().size();
        categoryService.delete(back.getId());
        int after = categoryService.findAll().size();
        Assert.assertEquals(before - 1, after);
        Assert.assertNull(categoryService.findById(back.getId()));
    }

    @Test
    public void subscribeUser() {
        CategoryDto categoryDto = new CategoryDto(GROUP, 0);
        CategoryDto back = categoryService.create(categoryDto);
        categoryService.subscribeUser(back.getId(), customerId);
        Assert.assertEquals(1, categoryService.findCategoriesForUser(customerId).size());
        Assert.assertTrue(categoryService.findCategoriesForUser(customerId).get(0).getName().equals(back.getName()));
    }

    @Test
    public void unsubscribeUser() {
        CategoryDto categoryDto = new CategoryDto(GROUP, 0);
        CategoryDto back = categoryService.create(categoryDto);
        categoryService.subscribeUser(back.getId(), customerId);
        categoryService.unsubscribeUser(back.getId(), customerId);
        Assert.assertEquals(0, categoryService.findCategoriesForUser(customerId).size());
    }

    @Test
    public void findCategoriesForUser() {
        CategoryDto categoryDto1 = new CategoryDto(ROMANTIC, 0);
        CategoryDto back1 = categoryService.create(categoryDto1);
        CategoryDto categoryDto2 = new CategoryDto(GROUP, 0);
        CategoryDto back2 = categoryService.create(categoryDto2);

        categoryService.subscribeUser(back1.getId(), customerId);
        categoryService.subscribeUser(back2.getId(), customerId);

        Assert.assertEquals(2, categoryService.findCategoriesForUser(customerId).size());
        Assert.assertTrue(categoryService.findCategoriesForUser(customerId).contains(back1));
        Assert.assertTrue(categoryService.findCategoriesForUser(customerId).contains(back2));
    }

}
