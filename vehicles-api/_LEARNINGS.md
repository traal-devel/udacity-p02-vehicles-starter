
Notes
* HATEOAS REST Services can be testes with the Spring Traverson class.
* Separate CarServiceTest for real unit test with h2 database. 

Questions:

* The Boogle Maps application does not actually store the address assigned to a
  given vehicle based on latitude and longitude, and instead randomly assigns a
  new one each time it is called. 
  * How could you update this to track which address is assigned to which vehicle? 
  
    * I would to do this on two places:
      1. Boogle Maps implementation itself. Per definition we should always receive the same
         address for a given longitude and latitude.
      2. The mapping between vehic
    
  * What happens if the vehicle latitude and longitude is updated in the Vehicles API?

Sources

* Hateoas
  * https://lankydan.dev/2017/09/18/testing-a-hateoas-service
  * https://lankydan.dev/2017/09/10/applying-hateoas-to-a-rest-api-with-spring-boot
  * https://github.com/spring-projects/spring-hateoas/blob/master/src/test/java/org/springframework/hateoas/client/TraversonTest.java
  * https://github.com/spring-projects/spring-hateoas-examples/tree/master/basics
  * https://stackoverflow.com/questions/37813797/deserialize-json-containing-links-and-embedded-using-spring-hateoas
  * https://docs.spring.io/spring-hateoas/docs/current/reference/html/#client.traverson
