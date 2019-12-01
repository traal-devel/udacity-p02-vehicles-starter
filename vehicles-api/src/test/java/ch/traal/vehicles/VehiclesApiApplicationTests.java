package ch.traal.vehicles;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.traal.vehicles.client.maps.MapsClient;
import ch.traal.vehicles.client.prices.PriceClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehiclesApiApplicationTests {

  
  /* member variables */
  @Autowired
  private PriceClient wcPrice;
  
  @Autowired
  private MapsClient  wcMaps;
  
  
  /* constructors */
  
  
  /* methods */
  @Test
  public void testNotNull() {
    
    assertNotNull(this.wcPrice);
    assertNotNull(this.wcMaps);
    
  }
  
  @Test
  public void contextLoads() {
    assertTrue(true);
  }

}
