package ch.traal.pricing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(PriceConfig.class)
public class PricingServiceApplicationTests {

  
  /* member variables */
  
  
  /* constructors */
  public PricingServiceApplicationTests() {
    super();
  }
  
  /* methods */
	@Test
	public void contextLoads() {
	  assertTrue(true);
	}

}
