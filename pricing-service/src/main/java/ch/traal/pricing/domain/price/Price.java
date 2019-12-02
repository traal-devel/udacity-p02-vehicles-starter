package ch.traal.pricing.domain.price;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
public class Price {

  
  /* member variables */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String currency;
  private BigDecimal price;
  private Long vehicleId;

  
  /* constructors */
  public Price() {
    super();
  }

  
  /* methods */
  public Price(String currency, BigDecimal price, Long vehicleId) {
    this.currency = currency;
    this.price = price;
    this.vehicleId = vehicleId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(Long vehicleId) {
    this.vehicleId = vehicleId;
  }
}
