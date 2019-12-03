package ch.traal.vehicles.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;

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
import ch.traal.vehicles.service.CarService;
import ch.traal.vehicles.test.util.DomainUtil;
import ch.traal.vehicles.test.util.StopOnFailureRunner;
import ch.traal.vehicles.test.util.TestOrder;

/**
 * CarService unit tests.
 * <p>
 * Info:<br />
 * The webclients are mocked but the instance of CarSerice uses a real h2 database.
 * </p>
 * 
 * @author traal-devel
 */
@RunWith(StopOnFailureRunner.class)
@SpringBootTest(classes = VehiclesApiApplication.class)
public class CarServiceTest {

  
  /* constants */
  public static final String DUMMY_PRICE = "USD 9999";
  
  
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
  
  
  /* constructors */
  public CarServiceTest() {
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
  }
  
  @Test
  @TestOrder(order = 0)
  public void testCreateCar() {
    Car carResult = this.carService.save(s_tmpCar);
    
    this.checkCarEquals(s_tmpCar, carResult);
    
    s_tmpCar = carResult;
  }
  
  @Test
  @TestOrder(order = 10)
  public void testFindByIdCar() {
    Car carResult = this.carService.findById(s_tmpCar.getId());
    Location loc = DomainUtil.createLocation(null, s_tmpCar.getLocation());
    
    this.checkCarEquals(s_tmpCar, carResult);
    this.checkLocactionEquals(loc, carResult.getLocation());
    assertEquals(CarServiceTest.DUMMY_PRICE, carResult.getPrice());
    
  }
  
  @Test
  @TestOrder(order = 20)
  public void testUpdateCar() {
    String color = "black";
    Car carResult = this.carService.findById(s_tmpCar.getId());
    carResult.getDetails().setExternalColor(color);
    s_tmpCar.getDetails().setExternalColor(color);
    
    carResult = this.carService.save(carResult);
    this.checkCarEquals(s_tmpCar, carResult);
  }
  
  @Test
  @TestOrder(order = 30)
  public void testAddFiveCars() {
    for(int i=0; i<5; i++) {
      s_tmpCar.setId(null);
      this.carService.save(s_tmpCar);
    }
    
    List<Car> carList = this.carService.list();
    assertNotNull(carList);
    assertTrue(carList.size() == 6);
  }
  
  @Test
  @TestOrder(order = 40)
  public void testRemoveAllCars() {
    List<Car> carList = this.carService.list();
    assertNotNull(carList);
    
    for (Car car : carList) {
      this.carService.delete(car.getId());
    }
    
    carList = this.carService.list();
    
    assertNotNull(carList);
    assertTrue(carList.size() == 0);
  }
  
  
  /**
   * Check if the given parameter are equals.
   * <p>
   * Price and Address are not checked, because they can change.
   * </p>
   * 
   * @param carLocal
   * @param carDB
   */
  private void checkCarEquals(Car carLocal, Car carDB) {
    assertEquals(carLocal.getId(), carDB.getId());
    if (carLocal.getLocation() != null && carDB.getLocation() != null) {
      assertEquals(carLocal.getLocation().getLat(), carDB.getLocation().getLat());
      assertEquals(carLocal.getLocation().getLon(), carDB.getLocation().getLon());
    }
    if (carLocal.getDetails() != null && carDB.getDetails() != null) {
      assertEquals(carLocal.getDetails().getBody(), carDB.getDetails().getBody());
      assertEquals(carLocal.getDetails().getEngine(), carDB.getDetails().getEngine());
      assertEquals(carLocal.getDetails().getExternalColor(), carDB.getDetails().getExternalColor());
      assertEquals(carLocal.getDetails().getFuelType(), carDB.getDetails().getFuelType());
      assertEquals(carLocal.getDetails().getManufacturer().getCode(), carDB.getDetails().getManufacturer().getCode());
      assertEquals(carLocal.getDetails().getManufacturer().getName(), carDB.getDetails().getManufacturer().getName());
      assertEquals(carLocal.getDetails().getMileage(), carDB.getDetails().getMileage());
      assertEquals(carLocal.getDetails().getModel(), carDB.getDetails().getModel());
      assertEquals(carLocal.getDetails().getModelYear(), carDB.getDetails().getModelYear());
      assertEquals(carLocal.getDetails().getNumberOfDoors(), carDB.getDetails().getNumberOfDoors());
    }
  }
  
  private void checkLocactionEquals(Location locLocal, Location locDB) {
    if (locLocal != null && locDB != null) {
      assertEquals(locLocal.getAddress(), locDB.getAddress());
      assertEquals(locLocal.getCity(), locDB.getCity());
      assertEquals(locLocal.getLat(), locDB.getLat());
      assertEquals(locLocal.getLon(), locDB.getLon());
      assertEquals(locLocal.getState(), locDB.getState());
      assertEquals(locLocal.getZip(), locDB.getZip());
    }
  }

}
