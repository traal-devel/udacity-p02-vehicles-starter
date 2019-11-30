# Vehicles API

You'll find the code related to our Vehicles API in the vehicles-api folder. 
It serves as a REST API to maintain vehicle data and to provide a complete 
view of vehicle details including price and address (obtained from the 
location and pricing services).

Let's take a quick look through the included files - and don't worry, you 
won't need to implement every single one of these! Note that every package 
is within com.udacity, so we won't include that part of the package name below.


## vehicles

### VehiclesApiApplication

This launches the Vehicles API as a Spring Boot application. Additionally, 
it initializes a few car manufacturers to place in the ManufacturerRepository, 
as well as creating the web clients to connect to the Maps and Pricing services.

## vehicles.api

### API Error
Declares a few quick methods to return errors and other messages from the 
Vehicles API.

### CarController
This is our actual REST controller for the application. This implements what 
happens when GET, POST, PUT and DELETE requests are received (using methods 
in the CarService), and how they are responded to (including formatting with 
CarResourceAssembler). You will implement these methods in your code.

### CarResourceAssembler
This helps mapping the CarController to the Car class to help return the 
API response.

### ErrorController
This helps to handle any invalid arguments fed to the API.

## vehicles.client.maps

### Address

Very similar to the Address file for boogle-maps, this declares a class for 
use with the MapsClient.

### MapsClient

Handles the format of a GET request to the boogle-maps WebClient to get 
location data.

## vehicles.client.prices

### Price

Very similar to the Price file for pricing-service, this declares a class 
for use with the PriceClient.

### PriceClient
Handles the format of a GET request to the pricing-service WebClient to get 
pricing data.

## vehicles.domain

### Condition
This enumerates the available values for the condition of a car (New or Used).

### Location
This declares information about the location of a vehicle. This is not the 
exact same as the Address class used by boogle-maps - it's primary use is 
related to the storage of latitude and longitude values. Because the data, 
such as address, gathered from boogle-maps is annotated as @Transient, 
this data is not stored until the next time boogle-maps is called.

## vehicles.domain.car

### Car

This declares certain information about a given vehicle, mostly that more about 
the car entry itself (such as CreatedAt). Note that a separate class, Details, 
also stores additional details about the car that is more specific to things 
like make, color and model. Note that similar to Location with data like 
address, this uses a @Transient tag with price, meaning the Pricing Service 
must be called each time a price is desired.

### CarRepository
This repository provide a type of data persistence while the web service runs, 
primarily related to vehicle information received in the CarService.

### Details
Declares additional vehicle details, primarily about the car build itself, such 
as fuelType and mileage.

## vehicles.domain.manufacturer

### Manufacturer
This declares the Manufacturer class, primarily just made of a ID code and 
name of manufacturer.

### ManufacturerRepository
This repository provide a type of data persistence while the web service runs, 
primarily to store manufacturer information like that initialized in 
VehiclesApiApplication.

## vehicles.domain

### CarNotFoundException
This creates a CarNotFoundException that can be thrown when an issue arises 
in the CarService.

### CarService
The Car Service does a lot of the legwork of the code. It can gather either 
the entire list of vehicles or just a single vehicle by ID (including calls 
to the maps and pricing web clients). It can also save updated vehicle 
information. Lastly, it can delete an existing car. All of these are called 
by the CarController based on queries to the REST API. You will implement 
most of these methods yourself.

## test/../vehicles.api

### CarControllerTest
Here, the various methods performed by the CarController are performed by 
creating mock calls to the Vehicles API. You will implement some of these 
methods yourself for great practice in building your own tests.
