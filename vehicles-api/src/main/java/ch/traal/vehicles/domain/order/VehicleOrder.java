package ch.traal.vehicles.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import ch.traal.vehicles.domain.car.Car;

/**
 * Declares class to hold car manufacturer information.
 */
/**
 * @author traal-devel
 *
 */
@Entity
public class VehicleOrder {

  
  /* member variables */
  @Id
  @GeneratedValue
  private Long id;
  
  @NotNull
  @OneToOne
  private Car           car;
  
  @NotNull
  private Long          buyerId;
  
  @CreatedDate
  private LocalDateTime purchasDate;
  
  @NotNull
  @Enumerated(EnumType.STRING)
  private OrderState    state;
  
  
  /* constructors */
  public VehicleOrder() {
    super();
  }

  
  /* methods */
  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public Car getCar() {
    return car;
  }


  public void setCar(Car car) {
    this.car = car;
  }


  public Long getBuyerId() {
    return buyerId;
  }


  public void setBuyerId(Long buyerId) {
    this.buyerId = buyerId;
  }


  public LocalDateTime getPurchasDate() {
    return purchasDate;
  }


  public void setPurchasDate(LocalDateTime purchasDate) {
    this.purchasDate = purchasDate;
  }

  public OrderState getState() {
    return state;
  }

  public void setState(OrderState state) {
    this.state = state;
  }
  

}
