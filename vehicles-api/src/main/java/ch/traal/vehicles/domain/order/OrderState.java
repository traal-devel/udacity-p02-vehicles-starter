package ch.traal.vehicles.domain.order;

/**
 * Available values for condition of a given car.
 */
public enum OrderState {
  NEW, 
  ENTERED, 
  HOLDING, 
  PRE_REJECTED, 
  REJECTED, 
  CHECKED, 
  PROCESSING, 
  CANCELLED,
  DELAYED, 
  ACCEPTED, 
  COMPLETED, 
  PENDING, 
  EXCEPTION
}