package ch.traal.vehicles.client.maps;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import ch.traal.vehicles.domain.Location;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapsClient {

  
  /* constants */
  public static final String WEBCLIENT_MAPS = "maps";
  private static final Logger log = LoggerFactory.getLogger(MapsClient.class);

  /** Cache map to store vehicle-ids lat lon to a certain location */
  private final static Map<Long, Location> CACHE_VEHICLE_ID_LOCATION 
                    = new PassiveExpiringMap<Long,Location>(60 * 60 * 1000); 

  
  /* member variables */
  private final WebClient     client;
  private final ModelMapper   mapper;
  

  
  /* constuctors */
  public MapsClient(
      @Qualifier(MapsClient.WEBCLIENT_MAPS) WebClient maps,
      ModelMapper mapper
  ) {
    this.client = maps;
    this.mapper = mapper;
  }

  /* methods */
  /**
   * Gets an address from the Maps client, given latitude and longitude.
   * 
   * @param location An object containing "lat" and "lon" of location
   * @return An updated location including street, city, state and zip,
   *   or an exception message noting the Maps service is down
   */
  public Location getAddress(Location location) {
    try {
      Address address = client
              .get()
              .uri(uriBuilder -> 
                    uriBuilder
                      .path("/maps/")
                      .queryParam("lat", location.getLat())
                      .queryParam("lon", location.getLon())
                      .build()
              )
              .retrieve()
              .bodyToMono(Address.class)
              .block();

      mapper.map(Objects.requireNonNull(address), location);

      return location;
    } catch (Exception e) {
        log.warn("Map service is down");
        return location;
    }
  }
  
  /**
   * Gets the address for the given parameters.
   * <p>
   * If the vehicle-id, lat and lon value was queried before then the cached
   * value is returned instead. 
   * </p>
   * <p>
   * The cache item is stored for an hour.
   * </p>
   * 
   * @param vehicleId Long - The vehiclie id of the car
   * @param paramLocation - Location (lat, lon)
   * @return Location - Location with address. 
   */
  public Location getCachedAddress(Long vehicleId, Location paramLocation) {
    Location cacheLocation = MapsClient.CACHE_VEHICLE_ID_LOCATION.get(vehicleId);
    Location retValue = null;
    
    if (cacheLocation != null && paramLocation != null 
        && Objects.equals(cacheLocation.getLat(), paramLocation.getLat()) 
        && Objects.equals(cacheLocation.getLon(), paramLocation.getLon())) {
      retValue = cacheLocation;
    } else {
      retValue = this.getAddress(paramLocation); 
      
      if (retValue != null) {
        MapsClient.CACHE_VEHICLE_ID_LOCATION.put(vehicleId, retValue);
      }
    }
    
    return retValue;
  }
  
  public void invalidateCacheBy(Long vehicleId) {
    MapsClient.CACHE_VEHICLE_ID_LOCATION.remove(vehicleId);
  }
}
