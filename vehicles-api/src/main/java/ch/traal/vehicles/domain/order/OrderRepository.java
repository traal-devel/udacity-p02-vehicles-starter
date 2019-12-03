package ch.traal.vehicles.domain.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.traal.vehicles.domain.car.Car;

@Repository
public interface OrderRepository 
       extends JpaRepository<VehicleOrder, Long> {

  
  /* methods */
  List<VehicleOrder> findByCar(Car c);
  
}
