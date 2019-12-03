package ch.traal.vehicles.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.domain.order.VehicleOrder;
import ch.traal.vehicles.service.CarService;
import ch.traal.vehicles.service.OrderService;
import ch.traal.vehicles.service.ex.CarNotFoundException;

/**
 * Proof of concept: Simple Order Controller
 * 
 * @author traal-devel
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  
  /* member variables */
  @Autowired
  private CarService    carService;
  
  @Autowired
  private OrderService  orderService;

  
  /* constructors */
  public OrderController() {
    super();
  }
  
  /* methods */
  @PostMapping
  public ResponseEntity<VehicleOrder> post (
      @RequestParam("buyerId") Long buyerId,
      @RequestParam("vehicleId") Long vehicleId
  ) { 
    Car car = this.carService.findById(vehicleId);
    if (car == null) {
      throw new CarNotFoundException();
    }
    
    VehicleOrder order = this.orderService.purchaseCar(buyerId, car);
    
    return ResponseEntity.ok(order);
  }

  @GetMapping
  public ResponseEntity<List<VehicleOrder>> list() {
    List<VehicleOrder> orderList = this.orderService.list();
    
    return ResponseEntity.ok(orderList);
  }
}
