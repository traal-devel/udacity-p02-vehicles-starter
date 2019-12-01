package ch.traal.vehicles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

import ch.traal.vehicles.client.maps.MapsClient;
import ch.traal.vehicles.client.prices.PriceClient;
import ch.traal.vehicles.domain.manufacturer.Manufacturer;
import ch.traal.vehicles.domain.manufacturer.ManufacturerRepository;

/**
 * Launches a Spring Boot application for the Vehicles API, initializes the car
 * manufacturers in the database, and launches web clients to communicate with
 * maps and pricing.
 */
@SpringBootApplication
@EnableJpaAuditing
public class VehiclesApiApplication {

  
  /* member variables */
  
  
  /* constructors */
  public VehiclesApiApplication() {
    super();
  }
  
  
  /* methods */
  public static void main(String[] args) {
    SpringApplication.run(VehiclesApiApplication.class, args);
  }

  /**
   * Initializes the car manufacturers available to the Vehicle API.
   * 
   * @param repository where the manufacturer information persists.
   * @return the car manufacturers to add to the related repository
   */
  @Bean
  CommandLineRunner initDatabase(ManufacturerRepository repository) {
    return args -> {
      repository.save(new Manufacturer(100, "Audi"));
      repository.save(new Manufacturer(101, "Chevrolet"));
      repository.save(new Manufacturer(102, "Ford"));
      repository.save(new Manufacturer(103, "BMW"));
      repository.save(new Manufacturer(104, "Dodge"));
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  /**
   * Web Client for the maps (location) API
   * 
   * @param endpoint where to communicate for the maps API
   * @return created maps endpoint
   */
  @Bean(name = MapsClient.WEBCLIENT_MAPS)
  public WebClient webClientMaps(
      @Value("${maps.endpoint}") String endpoint
  ) {
    return WebClient.create(endpoint);
  }

  /**
   * Web Client for the pricing API
   * 
   * @param endpoint where to communicate for the pricing API
   * @return created pricing endpoint
   */
  @Bean(name = PriceClient.WEBCLIENT_PRICING)
  public WebClient webClientPricing(
      @Value("${pricing.endpoint}") String endpoint
  ) {
    return WebClient.create(endpoint);
  }

}
