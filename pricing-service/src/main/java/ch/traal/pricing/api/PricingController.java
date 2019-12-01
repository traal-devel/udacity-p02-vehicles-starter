package ch.traal.pricing.api;

import ch.traal.pricing.domain.price.Price;
import ch.traal.pricing.service.PriceException;
import ch.traal.pricing.service.PricingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {

  
  /* member variables */
  @Autowired
  private PricingService pricingService;
  
  
  /* constructors */
  
  
  /* methods */
  /**
   * Gets the price for a requested vehicle.
   * 
   * @param vehicleId ID number of the vehicle for which the price is requested
   * @return price of the vehicle, or error that it was not found.
   */
  @GetMapping()
  public ResponseEntity<Price> get(
      @RequestParam Long vehicleId
  ) {
    try {
      Price price = pricingService.getPrice(vehicleId);
      return ResponseEntity.ok(price);
    } catch (PriceException ex) {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Price Not Found", ex);
    }
  }
}
