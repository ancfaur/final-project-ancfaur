package unit;

import offersApp.converter.offer.OfferConverter;
import offersApp.converter.offer.OfferConverterImpl;
import offersApp.dto.OfferDto;
import offersApp.dto.SearchDto;
import offersApp.entity.Discount;
import offersApp.entity.Offer;
import offersApp.entity.User;
import offersApp.entity.builder.OfferBuilder;
import offersApp.repository.OfferRepository;
import offersApp.repository.ReviewRepository;
import offersApp.service.offer.search.OfferSearchImpl;
import offersApp.service.offer.search.OfferSearchService;
import offersApp.service.offer.search.filter.FilterAssembler;
import offersApp.service.offer.search.order.OrderByPrice;
import offersApp.service.offer.search.order.OrderByStarsNo;
import offersApp.service.offer.search.order.Ordering;
import offersApp.service.offer.search.order.OrderingDelegator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static offersApp.constants.ApplicationConstants.Categories.ALL;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class SearchOffersServiceUnitTest {
    private OrderingDelegator orderingDelegator;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ReviewRepository reviewRepository;

    private OfferConverter offerConverter;
    private FilterAssembler filterAssembler;
    private OfferSearchService offerSearchService;


    @Before
    public void setup() {
        Ordering orderByPrice = new OrderByPrice();
        Ordering orderByDiscount = new OrderByPrice();
        Ordering orderByStarsNo = new OrderByStarsNo(reviewRepository);

        orderingDelegator = new OrderingDelegator(orderByPrice, orderByDiscount, orderByStarsNo);
        offerConverter = new OfferConverterImpl();
        filterAssembler = new FilterAssembler();
        offerSearchService = new OfferSearchImpl(orderingDelegator, offerRepository, offerConverter, filterAssembler);

        Discount discount1 = new Discount(6, 20);
        Discount discount2 = new Discount(2, 30);
        Discount discount3 = new Discount(5, 10);

        List<Offer> allOffers = new ArrayList<>();
        Offer offer1 = new OfferBuilder()
                .setName("caluti")
                .setPrice(50)
                .setInStock(20)
                .setLocation("Padurea Baciu")
                .setDescription("E fain sa calaresti cu noi")
                .setAgent(new User())
                .setImage("")
                .setNoPersons(1)
                .setDatePublished(new Date())
                .setCategories(new ArrayList<>())
                .setInitialNo(40)
                .setDiscount(discount1)
                .build();
        Offer offer2 = new OfferBuilder()
                .setName("tras cu arma")
                .setPrice(100)
                .setInStock(20)
                .setLocation("Poligon de tragere")
                .setDescription("E fain")
                .setAgent(new User())
                .setImage("")
                .setNoPersons(10)
                .setDatePublished(new Date())
                .setCategories(new ArrayList<>())
                .setInitialNo(40)
                .setDiscount(discount2)
                .build();
        Offer offer3 = new OfferBuilder()
                .setName("hotel 3 stele")
                .setPrice(180)
                .setInStock(2)
                .setLocation("Venetia")
                .setDescription("hai cu mine")
                .setAgent(new User())
                .setImage("")
                .setNoPersons(5)
                .setDatePublished(new Date())
                .setCategories(new ArrayList<>())
                .setInitialNo(40)
                .setDiscount(discount3)
                .build();
        allOffers.add(offer1);
        allOffers.add(offer2);
        allOffers.add(offer3);
        when(offerRepository.findAll()).thenReturn(allOffers);
        when(reviewRepository.findByOffer(any(Offer.class))).thenReturn(new ArrayList<>());
    }

    @Test
    public void noFilter() {
        List<String> categories = new ArrayList<>();
        categories.add(ALL);
        SearchDto searchDto = new SearchDto("", categories, 0, 0, 0, 0, 0, "DATE");
        List<OfferDto> offerDtos = offerSearchService.searchBy(searchDto);
        Assert.assertEquals(offerDtos.size(), 3);
    }

    @Test
    public void filterPrice() {
        List<String> categories = new ArrayList<>();
        categories.add(ALL);
        SearchDto searchDto = new SearchDto("", categories, 60, 1000, 0, 0, 0, "DATE");
        List<OfferDto> offerDtos = offerSearchService.searchBy(searchDto);
        Assert.assertEquals(2, offerDtos.size());
    }

    @Test
    public void filterPriceAndKeyword() {
        List<String> categories = new ArrayList<>();
        categories.add(ALL);
        SearchDto searchDto = new SearchDto("hotel", categories, 60, 1000, 0, 0, 0, "DATE");
        List<OfferDto> offerDtos = offerSearchService.searchBy(searchDto);
        Assert.assertEquals(1, offerDtos.size());
    }

    @Test
    public void filterPriceAndKeywordAndDiscountPercentage() {
        List<String> categories = new ArrayList<>();
        categories.add(ALL);
        SearchDto searchDto = new SearchDto("hotel", categories, 60, 1000, 0, 0, 100, "DATE");
        List<OfferDto> offerDtos = offerSearchService.searchBy(searchDto);
        Assert.assertEquals(0, offerDtos.size());
    }
}
