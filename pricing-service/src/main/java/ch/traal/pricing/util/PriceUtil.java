package ch.traal.pricing.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class PriceUtil {

  
  /* member variables */

  
  /* constructors */
  private PriceUtil() {
    super();
  }

  /* methods */
  /**
   * Gets a random price to fill in for a given vehicle ID.
   * @return random price for a vehicle
   */
  public static BigDecimal randomPrice() {
      return 
          new BigDecimal(
                ThreadLocalRandom.current().nextDouble(1, 5)
              ).multiply(
                new BigDecimal(5000d)
              ).setScale(2, RoundingMode.HALF_UP);
  }

}
