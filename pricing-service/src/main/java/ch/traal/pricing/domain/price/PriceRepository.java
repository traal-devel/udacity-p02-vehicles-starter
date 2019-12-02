package ch.traal.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * :DONE: The Pricing Service API is converted to a microservice with 
 * Spring Data REST, without the need to explicitly include code for the 
 * Controller or Service.
 * 
 * @author traal-devel
 */
public interface PriceRepository extends CrudRepository<Price, Long>{

  
  /* methods */
  Price findByVehicleId(Long vehicleId);
}
