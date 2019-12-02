package ch.traal.pricing;

import java.util.Map;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import ch.traal.pricing.domain.price.Price;
import ch.traal.pricing.domain.price.PriceRepository;
import ch.traal.pricing.util.PriceUtil;

/**
 * Test configuratoni which is only executed in the test-mode.
 * 
 * POC: Initialize the database only for unit test cases.
 * 
 * @author traal-devel
 */
@TestConfiguration
public class PriceConfig {

  
  /* member variables */

  
  /* constructors */
  public PriceConfig() {
    super();
  }
 
  /* methods */
  @Bean
  CommandLineRunner initDatabase(PriceRepository repository) {
    return args -> {
      // :TODO: Refactor this... use one loop.
      /**
       * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
       */
      Map<Long, Price> hmPrice = 
          LongStream
          .range(1, 20)
          .mapToObj(i -> new Price("USD", PriceUtil.randomPrice(), i))
          .collect(Collectors.toMap(Price::getVehicleId, p -> p));
      
      for (Map.Entry<Long,Price> entPrice : hmPrice.entrySet()) {
        repository.save(entPrice.getValue());
      }
    };
  }

}
