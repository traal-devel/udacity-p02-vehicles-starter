package ch.traal.pricing;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ch.traal.pricing.domain.price.Price;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(PriceConfig.class)
public class PricingControllerIntegrationTest {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger(PricingControllerUnitTest.class);
  
  
  /* member variables */
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  
  /* constructors */
  public PricingControllerIntegrationTest() {
    super();
  }

  
  /* methods */
  @Test
  public void getPriceByVehicleId_1_Test() {
    ResponseEntity<Price> response = 
        this.restTemplate
              .getForEntity(
                "http://localhost:" + port + "/services/price?vehicleId=1", 
                Price.class
              );
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertNotNull(response.getBody());
    
    Price price = response.getBody();
    logger.info(
      " Vehicle-ID: " + price.getVehicleId() + 
      " Currency: " + price.getCurrency() + 
      " Price: " + price.getPrice()
    );
  }
  
  /**
   * Vehicle ids greater than 20 result in an HTTP_NOT_FOUND.
   */
  @Test
  public void getPriceByVehicleId_21_Test() {
    ResponseEntity<Price> response = 
        this.restTemplate
              .getForEntity(
                "http://localhost:" + port + "/services/price?vehicleId=21", 
                Price.class
              );
    assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    assertNotNull(response.getBody());
  }
  
  

}
