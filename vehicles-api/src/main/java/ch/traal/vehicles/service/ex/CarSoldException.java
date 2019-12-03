package ch.traal.vehicles.service.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.NOT_ACCEPTABLE, 
    reason = "Car already sold."
)
public class CarSoldException extends RuntimeException {

  /* constants */
  /** Added by eclipse */
  private static final long serialVersionUID = -938899661026042794L;
  
  
  /* member variables */

  
  /* constructors */
  public CarSoldException() {
    super();
  }

  public CarSoldException(String message) {
    super(message);
  }
  
  /* methods */

}
