
# Location Service Code (Boogle Maps)
You'll find the code related to our location service in the boogle-maps folder. 
It serves as a Mock to simulate a Maps WebService where, given a latitude and 
longitude, will return a random address.

**You won't have to implement anything as part of this application**, but let's 
take a quick look through the included files. Note that every package is within 
com.udacity, so we won't include that part of the package name below.

## boogle.maps

### Address

This declares the Address class, primarily just made of the private variables 
address, city, state and zip. Note that the latitude and longitude are 
not stored here - they come from the Vehicles API.

### BoogleMapsApplication

This launches Boogle Maps as a Spring Boot application.

### MapsController

This is our actual REST controller for the application. This implements what a 
GET request will respond with - in our case, since it is a Mock of a 
WebService, we are just responding with a random address from the repository.

### MockAddressRepository

Repositories normally provide some type of data persistence while the web 
service runs. In this case, this Mock is simply choosing a random address 
from the ADDRESSES array defined in the file.