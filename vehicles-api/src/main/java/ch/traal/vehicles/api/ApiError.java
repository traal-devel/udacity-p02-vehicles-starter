package ch.traal.vehicles.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Declares methods to return errors and other messages from the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

  
  /* member variables */
  private final String message;
  private final List<String> errors;

  
  /* constructors */
  ApiError(String message, List<String> errors) {
    this.message = message;
    this.errors = errors;
  }

  
  /* methods */
  public String getMessage() {
    return message;
  }

  public List<String> getErrors() {
    return errors;
  }
}
