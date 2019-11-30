package ch.traal.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import ch.traal.vehicles.domain.car.Car;

/**
 * Maps the CarController to the Car class using HATEOAS
 */
@Component
public class CarResourceAssembler implements ResourceAssembler<Car, Resource<Car>> {

  
  /* member variables */
  
  
  /* constructors */
  
  
  /* methods */
  @Override
  public Resource<Car> toResource(Car car) {
      return new Resource<>(car,
              linkTo(methodOn(CarController.class).get(car.getId())).withSelfRel(),
              linkTo(methodOn(CarController.class).list()).withRel("cars"));

  }
}
