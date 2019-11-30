package ch.traal.vehicles.domain.manufacturer;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Declares class to hold car manufacturer information.
 */
@Entity
public class Manufacturer {

  
  /* member variables */
  @Id
  private Integer code;
  private String name;

  
  /* constructors */
  public Manufacturer() {
    super();
  }

  
  /* methods */
  public Manufacturer(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public Integer getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
