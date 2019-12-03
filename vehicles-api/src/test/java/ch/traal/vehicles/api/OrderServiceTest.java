package ch.traal.vehicles.api;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ch.traal.vehicles.VehiclesApiApplication;
import ch.traal.vehicles.client.maps.MapsClient;
import ch.traal.vehicles.client.prices.PriceClient;
import ch.traal.vehicles.domain.Location;
import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.domain.car.CarRepository;
import ch.traal.vehicles.domain.order.VehicleOrder;
import ch.traal.vehicles.service.CarService;
import ch.traal.vehicles.service.OrderService;
import ch.traal.vehicles.test.util.DomainUtil;
import ch.traal.vehicles.test.util.StopOnFailureRunner;

/**
 * OrderService unit tests.
 * 
 * 
 * @author traal-devel
 */
@RunWith(StopOnFailureRunner.class)
@SpringBootTest(classes = VehiclesApiApplication.class)
public class OrderServiceTest {
  
  /* constants */
  private static final Logger logger = LogManager.getLogger(OrderServiceTest.class);

  /* class variables */
  private static Car s_tmpCar = null;
  
  /* member variables */
  private CarService      carService;
  
  @Autowired
  private CarRepository   repository;
  
  @MockBean
  private MapsClient      webClientMaps;
  
  @MockBean
  private PriceClient     webClientPricing;
  
  @Autowired
  private OrderService    orderService;
  
  /* constructors */
  public OrderServiceTest() {
    super();
  }

  
  /* methods */
  @BeforeClass
  public static void beforeClass() {
    s_tmpCar = DomainUtil.createCar(null);
  }
  
  @Before
  public void before() {
    Location loc = DomainUtil.createLocation(null, s_tmpCar.getLocation());
    
    given(this.webClientMaps.getAddress(any())).willReturn(loc);
    given(this.webClientPricing.getPrice(any())).willReturn(CarServiceTest.DUMMY_PRICE);
    
    this.carService = new CarService(repository, webClientMaps, webClientPricing);
    
  }
  
  @Test
  public void testNotNull() {
    assertNotNull(this.carService);
    assertNotNull(this.orderService);
  }
  
  @Test
  public void testBasic() {
    Car carResult = this.carService.save(s_tmpCar);
    
    assertNotNull(carResult);
  }
  
  @Test
  public void testBasic2() {
    Car carResult = this.carService.save(s_tmpCar);
    try {
    VehicleOrder order = this.orderService.purchaseCar(1L, carResult);

    assertNotNull(order);
    logger.info("New order id: " + order.getId());
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }

}
