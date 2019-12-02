package ch.traal.vehicles.test.util;

import org.springframework.util.StringUtils;

import ch.traal.vehicles.domain.Condition;
import ch.traal.vehicles.domain.Location;
import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.domain.car.Details;
import ch.traal.vehicles.domain.manufacturer.Manufacturer;

public class DomainUtil {

  /* member variables */

  /* constructors */
  private DomainUtil() {
    super();
  }
  
  /* methods */
  /**
   * Creates a car instance with default values. Id is set by using 
   * given parameter.
   * 
   * @param id Long - Car id, if null or less than 0 then the id is not set.
   * @return The new car instance
   */
  public static Car createCar(
      final Long id
  ) {
    Car car = new Car();
    if (id != null && id > -1) {
      car.setId(id);
    }
    car.setLocation(new Location(40.730610, -73.935242));
    car.setCondition(Condition.USED);
    Details details = new Details();
    details.setModel("Impala");
    details.setMileage(32280);
    details.setExternalColor("white");
    details.setBody("sedan");
    details.setEngine("3.6L V6");
    details.setFuelType("Gasoline");
    details.setModelYear(2018);
    details.setProductionYear(2018);
    details.setNumberOfDoors(4);
    car.setDetails(details);
    car.setPrice(null);

    Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
    details.setManufacturer(manufacturer);
    
    return car;
    
  }
  
  public static Location createLocation(
      final String suffix, 
      final Location loc
  ) {
    Location retLoc = new Location(loc.getLat(), loc.getLon());
    String tmpSuffix = StringUtils.hasText(suffix) ? "_" + suffix : "";
        
    retLoc.setAddress("TEST_ADDRESS" + tmpSuffix);
    retLoc.setCity("TEST_CITY" + tmpSuffix);
    retLoc.setState("TEST_STATE_" + tmpSuffix);
    retLoc.setZip("TEST_ZIP_" + tmpSuffix);
    
    return retLoc;
  }
  

}
