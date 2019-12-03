package ch.traal.vehicles.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ch.traal.vehicles.client.maps.MapsClient;
import ch.traal.vehicles.client.prices.PriceClient;
import ch.traal.vehicles.domain.Location;
import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.domain.car.CarRepository;
import ch.traal.vehicles.service.ex.CarNotFoundException;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger();
  
  
  /* member variables */
  private final CarRepository   repository;
  private final MapsClient      webClientMaps;
  private final PriceClient     webClientPricing;

  
  /* constructors */
  public CarService(
      CarRepository repository,
      MapsClient webClientMaps,
      PriceClient webClientPricing
  ) {
    
    /**
     * :DONE: Add the Maps and Pricing Web Clients you create
     *   in `VehiclesApiApplication` as arguments and set them here.
     */
    this.repository = repository;
    this.webClientMaps = webClientMaps;
    this.webClientPricing = webClientPricing;
    
  }

  
  /* methods */
  /**
   * Gathers a list of all vehicles
   * @return a list of all vehicles in the CarRepository
   */
  public List<Car> list() {
    return repository.findAll();
  }

  /**
   * Gets car information by ID (or throws exception if non-existent)
   * 
   * @param id - The ID number of the car to gather information on
   * @return The requested car's information, including location and price
   */
  public Car findById(Long id) {
    
    /**
     * :DONE: Find the car by ID from the `repository` if it exists.
     *   If it does not exist, throw a CarNotFoundException
     *   Remove the below code as part of your implementation.
     */
    Car car = this.repository
                  .findById(id)
                  .orElseThrow(CarNotFoundException::new);
    
    /**
     * :DONE: Use the Pricing Web client you create in `VehiclesApiApplication`
     *   to get the price based on the `id` input'
     *   :jok: -> Use the wrapper PriceClient + MapsClient.  
     *   
     * :DONE: Set the price of the car
     * 
     * Note: The car class file uses @transient, meaning you will need to call
     *   the pricing service each time to get the price.
     */
    String strPrice = this.webClientPricing.getPrice(car.getId());
    car.setPrice(strPrice);

    /**
     * :DONE: Use the Maps Web client you create in `VehiclesApiApplication`
     *   to get the address for the vehicle. You should access the location
     *   from the car object and feed it to the Maps service.
     *   
     * :DONE: Set the location of the vehicle, including the address information
     * 
     * Note: The Location class file also uses @transient for the address,
     * meaning the Maps service needs to be called each time for the address.
     */
    Location carLocation = car.getLocation();
    Location mapLocation = this.webClientMaps.getCachedAddress(id, carLocation);
    car.setLocation(mapLocation);

    return car;
  }

  /**
   * Either creates or updates a vehicle, based on prior existence of car.
   * 
   * @param car - A car object, which can be either new or existing
   * @return The new/updated car is stored in the repository
   */
  public Car save(Car car) {
    Car carDB = null;
    
    if (car.getId() != null) {
      carDB = repository.findById(car.getId())
              .map(carToBeUpdated -> {
                  carToBeUpdated.setDetails(car.getDetails());
                  carToBeUpdated.setLocation(car.getLocation());
                  carToBeUpdated.setCondition(car.getCondition());
                  return repository.save(carToBeUpdated);
              }).orElseThrow(CarNotFoundException::new);
      
      
    } else {
      carDB = repository.save(car);
      
      // :INFO: Extension, If we save a new car to our database then we get a 
      // quote (random price) for this vehicle-id.
      String strPrice = this.webClientPricing.getQuote(carDB.getId());
      logger.info("Quoted price " + strPrice + " for vehiclie-id " + carDB.getId());
    }

    return carDB;
  }

  /**
   * Deletes a given car by ID.
   * 
   * @param id - The ID number of the car to delete
   */
  public void delete(Long id) {
    /**
     * :DONE: Find the car by ID from the `repository` if it exists.
     *   If it does not exist, throw a CarNotFoundException
     */
    Car car = this.repository
                    .findById(id)
                    .orElseThrow(CarNotFoundException::new);
    this.webClientPricing.removeQuote(id);
    this.webClientMaps.invalidateCacheBy(id);

    /**
     * :DONE: Delete the car from the repository.
     */
    this.repository.delete(car);
  }
}
