package ch.traal.pricing.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.traal.pricing.domain.price.Price;
import ch.traal.pricing.domain.price.PriceRepository;
import ch.traal.pricing.service.PriceException;
import ch.traal.pricing.service.PricingService;
import ch.traal.pricing.util.PriceUtil;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {

  
  /* member variables */
  @Autowired
  private PricingService pricingService;
  
  @Autowired
  private PriceRepository priceRepository;
  
  /* constructors */
  public PricingController() {
    super();
  }
  
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
  
  @GetMapping("/quote")
  public ResponseEntity<Price> getQuoteForVehicleId(
      @RequestParam Long vehicleId
  ) {
    BigDecimal randomPrice = PriceUtil.randomPrice();
    Price price = new Price();
    price.setCurrency("USD");
    price.setVehicleId(vehicleId);
    price.setPrice(randomPrice);
    
    this.priceRepository.save(price);
    
    return ResponseEntity.ok(price);
  }
  
}
