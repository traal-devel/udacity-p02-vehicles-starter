package ch.traal.vehicles.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.domain.order.VehicleOrder;
import ch.traal.vehicles.domain.order.OrderRepository;
import ch.traal.vehicles.domain.order.OrderState;
import ch.traal.vehicles.service.ex.CarSoldException;

/**
 * Proof of concept: Implements a simple order service.
 */
@Service
public class OrderService {

  
  /* constants */
  private static final Logger logger = LogManager.getLogger();
  
  
  /* member variables */
  @Autowired
  private OrderRepository   repository;
  
  
  /* constructors */
  public OrderService() {
    super();
  }

  /* methods */

  public VehicleOrder purchaseCar(Long buyerId, Car car) {
    
    List<VehicleOrder> orderList = this.repository.findByCar(car);
    // If an order with the vehicle id exists then throw car sold exception
    // else create a new order for this buyer
    if (orderList != null && orderList.size() > 0) {
      throw new CarSoldException();
    }
    
    VehicleOrder order = new VehicleOrder();
    order.setBuyerId(buyerId);
    order.setCar(car);
    order.setState(OrderState.NEW);
    
    VehicleOrder orderDB = this.repository.save(order);
    
    return orderDB;

  }

  public List<VehicleOrder> list() {
    return repository.findAll();
  }
}
