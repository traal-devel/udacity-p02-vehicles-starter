package ch.traal.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.traal.vehicles.domain.car.Car;
import ch.traal.vehicles.service.CarService;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {
  
  
  /* member variables */
  private final CarService carService;
  private final CarResourceAssembler assembler;

  
  /* constructors */
  /**
   * Constructor for CarController.
   * 
   * @param carService - Service injected by spring
   * @param assembler - Assembler instance injected by spring
   */
  CarController(
      CarService carService, 
      CarResourceAssembler assembler
  ) {
    this.carService = carService;
    this.assembler = assembler;
  }

  
  /* methods */
  /**
   * Creates a list to store any vehicles.
   * 
   * @return list of vehicles
   */
  @GetMapping
  Resources<Resource<Car>> list() {
    List<Resource<Car>> resources = 
            carService
                .list()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
    
    return new Resources<>(
                  resources, 
                  linkTo(
                      methodOn(CarController.class).list()
                  ).withSelfRel()
               );
  }

  /**
   * Gets information of a specific car by ID.
   * 
   * @param id - The id number of the given vehicle
   * @return All information for the requested vehicle
   */
  @GetMapping("/{id}")
  Resource<Car> get(
      @PathVariable Long id
  ) {
    
    /**
     * :DONE: Use the `findById` method from the Car Service to get car information.
     * :DONE: Use the `assembler` on that car and return the resulting output. 
     *    Update the first line as part of the above implementing. 
     *    ---> :CHECK: jk, ??? -> I think, I've done this correctly...  
     */
    Car car = this.carService.findById(id);
    return assembler.toResource(car);
    
  }

  /**
   * Posts information to create a new vehicle in the system.
   * 
   * @param car - A new vehicle to add to the system.
   * @return response that the new vehicle was added to the system
   * @throws URISyntaxException if the request contains invalid fields or syntax
   */
  @PostMapping
  ResponseEntity<?> post(
      @Valid @RequestBody Car car
  ) throws URISyntaxException {
  
    /**
     * :DONE: Use the `save` method from the Car Service to save the input car. 
     * :DONE: Use the `assembler` on that saved car and return as part of the response.
     *   Update the first line as part of the above implementing.
     */
    Car carDB = this.carService.save(car);
    Resource<Car> resource = assembler.toResource(carDB);
    
    return ResponseEntity
                .created(
                  new URI(resource.getId().expand().getHref())
                ).body(resource);
    
  }

  /**
   * Updates the information of a vehicle in the system.
   * 
   * @param id - The ID number for which to update vehicle information.
   * @param car - The updated information about the related vehicle.
   * @return response that the vehicle was updated in the system
   */
  @PutMapping("/{id}")
  ResponseEntity<?> put(
      @PathVariable Long id, 
      @Valid @RequestBody Car car
  ) {
  
    /**
     * :DONE: Set the id of the input car object to the `id` input. 
     * :DONE: Save the car using the `save` method from the Car service 
     * TODO: Use the `assembler` on that updated car and return as part of 
     *   the response. Update the first line as part of the above implementing.
     */
    car.setId(id);
    Car carDB = this.carService.save(car);
    
    Resource<Car> resource = assembler.toResource(carDB);
    return ResponseEntity.ok(resource);
    
  }

  /**
   * Removes a vehicle from the system.
   * 
   * @param id - The ID number of the vehicle to remove.
   * @return response that the related vehicle is no longer in the system
   */
  @DeleteMapping("/{id}")
  ResponseEntity<?> delete(
      @PathVariable Long id
  ) {
    
    /**
     * :DONE: Use the Car Service to delete the requested vehicle.
     */
    this.carService.delete(id);
    
    return ResponseEntity.noContent().build();
    
  }
}
