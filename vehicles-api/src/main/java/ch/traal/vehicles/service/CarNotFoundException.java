package ch.traal.vehicles.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.NOT_FOUND, 
    reason = "Car not found"
)
public class CarNotFoundException extends RuntimeException {

  
  /* constants */
  /** Added by eclipse */
  private static final long serialVersionUID = -938899661026042794L;

  
  /* member variables */
  

  /* constructors */
  public CarNotFoundException() {
    super();
  }

  public CarNotFoundException(String message) {
    super(message);
  }
}
