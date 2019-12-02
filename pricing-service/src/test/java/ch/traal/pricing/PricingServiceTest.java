package ch.traal.pricing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.traal.pricing.domain.price.Price;
import ch.traal.pricing.domain.price.PriceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PricingServiceApplication.class)
public class PricingServiceTest {

  
  /* member variables */
  @Autowired
  private PriceRepository repository;

  
  /* constructors */
  public PricingServiceTest() {
    super();
  }
  
  
  /* methods */
  @Test
  public void testNotNull() {
    assertNotNull(this.repository);
  }
  
  @Test
  public void testFindByVehicleId() {
    Price price = this.repository.findByVehicleId(1L);
    assertNull(price);
  }
  
  @Test
  public void testInsertAndRead() {
    Long vehicleId = 10L;
    
    Price price = new Price();
    price.setCurrency("USD");
    price.setPrice(new BigDecimal("999.99"));
    price.setVehicleId(vehicleId);
    
    Price priceDB = this.repository.save(price);
    
    assertNotNull(priceDB);
    assertEquals(price.getCurrency(), priceDB.getCurrency());
    assertEquals(price.getPrice(), priceDB.getPrice());
    assertEquals(price.getVehicleId(), priceDB.getVehicleId());
    
    priceDB = this.repository.findByVehicleId(vehicleId);
    assertNotNull(priceDB);
    assertEquals(price.getCurrency(), priceDB.getCurrency());
    assertEquals(price.getPrice(), priceDB.getPrice());
    assertEquals(price.getVehicleId(), priceDB.getVehicleId());
  }

}
