package ch.traal.pricing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.traal.pricing.domain.price.Price;
import ch.traal.pricing.domain.price.PriceRepository;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {

  
  /* member variabels */
  @Autowired
  private PriceRepository priceRepository;
  
  
  /* constructors */
  public PricingService() {
    super();
  }
  
  
  /* methods */
  /**
   * If a valid vehicle ID, gets the price of the vehicle from the stored array.
   * 
   * @param vehicleId ID number of the vehicle the price is requested for.
   * @return price of the requested vehicle
   * @throws PriceException vehicleID was not found
   */
  public Price getPrice(Long vehicleId) throws PriceException {
    Price priceDB = priceRepository.findByVehicleId(vehicleId);

//    if (!PRICES.containsKey(vehicleId)) {
//      throw new PriceException("Cannot find price for Vehicle " + vehicleId);
//    }
    if (priceDB == null) {
      throw new PriceException("Cannot find price for Vehicle " + vehicleId);
    }

    return priceDB;
  }

}
