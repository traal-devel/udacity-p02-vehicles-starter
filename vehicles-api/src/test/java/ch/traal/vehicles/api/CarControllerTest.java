package ch.traal.vehicles.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.traal.vehicles.client.maps.MapsClient;
import ch.traal.vehicles.client.prices.PriceClient;
import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.service.CarService;
import ch.traal.vehicles.test.util.DomainUtil;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(CarControllerTest.class);
  
  /* member variables */
  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Car> json;

  @MockBean
  private CarService carService;

  // jok, Test with WebClients are done in the class CarServiceTest
//  @MockBean
//  private PriceClient priceClient;
//
//  @MockBean
//  private MapsClient mapsClient;
  
  @LocalServerPort
  private int port;
  
  
  /* constructors */
  public CarControllerTest() {
    super();
  }
  
  
  /* methods */
  /**
   * Creates pre-requisites for testing, such as an example car.
   */
  @Before
  public void setup() {
    Car car = DomainUtil.createCar(1L);
    
    given(carService.save(any())).willReturn(car);
    given(carService.findById(any())).willReturn(car);
    given(carService.list()).willReturn(Collections.singletonList(car));
    
  }

  /**
   * Tests for successful creation of new car in the system
   * 
   * @throws Exception when car creation fails in the system
   */
  @Test
  public void createCar() throws Exception {
    Car car = DomainUtil.createCar(1L);
    mvc.perform(
            post(new URI("/cars"))
              .content(json.write(car).getJson())
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isCreated());
  }

  /**
   * Tests if the read operation appropriately returns a list of vehicles.
   * 
   * @throws Exception if the read operation of the vehicle list fails
   */
  @Test
  public void listCars() throws Exception {
    /**
     * :DONE: Add a test to check that the `get` method works by calling
     *   the whole list of vehicles. This should utilize the car from `getCar()`
     *   below (the vehicle will be the first in the list).
     */
    Car car = DomainUtil.createCar(1L);
    this.mvc.perform(
          get(new URI("/cars"))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{}"))
        .andExpect(jsonPath("$._embedded.carList[0].location.lat", is(car.getLocation().getLat())))
        .andExpect(jsonPath("$._embedded.carList[0].location.lon", is(car.getLocation().getLon())))
        .andExpect(jsonPath("$._embedded.carList[0].condition", is(car.getCondition().toString())))
        .andExpect(jsonPath("$._embedded.carList[0].details.manufacturer.code", is(car.getDetails().getManufacturer().getCode())))   
        .andExpect(jsonPath("$._embedded.carList[0].details.manufacturer.name", is(car.getDetails().getManufacturer().getName())))   
        .andExpect(jsonPath("$._embedded.carList[0].details.model", is(car.getDetails().getModel())))
        .andExpect(jsonPath("$._embedded.carList[0].details.mileage", is(car.getDetails().getMileage())))
        .andExpect(jsonPath("$._embedded.carList[0].details.externalColor", is(car.getDetails().getExternalColor())))
        .andExpect(jsonPath("$._embedded.carList[0].details.body", is(car.getDetails().getBody())))
        .andExpect(jsonPath("$._embedded.carList[0].details.engine", is(car.getDetails().getEngine())))
        .andExpect(jsonPath("$._embedded.carList[0].details.fuelType", is(car.getDetails().getFuelType())))
        .andExpect(jsonPath("$._embedded.carList[0].details.modelYear", is(car.getDetails().getModelYear())))
        .andExpect(jsonPath("$._embedded.carList[0].details.productionYear", is(car.getDetails().getProductionYear())))
        .andExpect(jsonPath("$._embedded.carList[0].details.numberOfDoors", is(car.getDetails().getNumberOfDoors())));
    
    // :INFO: Check if the method list was invoked once.
    verify(this.carService, times(1)).list();
  }
  
  /**
   * Basically the same test as above but this time using the hateoas Traverson
   * class
   * 
   * @throws Exception if the read operation of the vehicle list fails
   */
  @Test
  public void listCarsUsingTraverson() throws Exception {
    Car carDummy = DomainUtil.createCar(1L);
    TypeReferences.ResourcesType<Resource<Car>> resourceParameterizedTypeReference =
           new TypeReferences.ResourcesType<Resource<Car>>(){};

    Traverson traverson = new Traverson(new URI("http://localhost:" + this.port + "/cars"), MediaTypes.HAL_JSON);
    Traverson.TraversalBuilder builder = traverson.follow();
    Resources<Resource<Car>> taskResources = 
                      builder.toObject(resourceParameterizedTypeReference);
    
    assertNotNull(taskResources);
    assertNotNull(taskResources.getContent());
    assertTrue(taskResources.getContent().size() > 0);
    
    Car carRemote = taskResources.getContent().stream().findFirst().get().getContent();
    
    assertEquals(carDummy.getLocation().getLat(), carRemote.getLocation().getLat());
    assertEquals(carDummy.getLocation().getLon(), carRemote.getLocation().getLon());

    logger.info(json.write(carRemote).getJson());
  }

  /**
   * Tests the read operation for a single car by ID.
   * 
   * @throws Exception if the read operation for a single car fails
   */
  @Test
  public void findCar() throws Exception {
    /**
     * :DONE: Add a test to check that the `get` method works by calling
     *   a vehicle by ID. This should utilize the car from `getCar()` below.
     */
    Car car = DomainUtil.createCar(1L);
    this.mvc.perform(
              get(new URI("/cars/" + car.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{}"))
            .andExpect(jsonPath("$.id", is(car.getId().intValue())));
    verify(this.carService, times(1)).findById(car.getId());
  }

  /**
   * Tests the deletion of a single car by ID.
   * @throws Exception if the delete operation of a vehicle fails
   */
  @Test
  public void deleteCar() throws Exception {
    /**
     * :DONE: Add a test to check whether a vehicle is appropriately deleted
     *   when the `delete` method is called from the Car Controller. This
     *   should utilize the car from `getCar()` below.
     */
    Car car = DomainUtil.createCar(1L);
    this.mvc.perform(
                delete(new URI("/cars/" + car.getId()))
            )
            .andExpect(status().isNoContent());
    // Otherwise an error would be thrown.
  }

}