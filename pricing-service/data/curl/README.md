
## Add a price to the database

curl -X POST \
  http://localhost:8082/prices \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 61' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8082' \
  -H 'User-Agent: PostmanRuntime/7.20.1' \
  -H 'cache-control: no-cache' \
  -d '{ 
	"currency" : "USD",
	"price" : 999.99,
	"vehicleId" : 1
}'


## Get the price using the vehicle-id=1

curl -X GET \
  http://localhost:8082/prices/1 \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8082' \
  -H 'Postman-Token: 643aba53-d3b7-49b4-8904-9f225959e2aa,c0e02920-ce3a-4ee1-aacf-afd15b39f321' \
  -H 'User-Agent: PostmanRuntime/7.20.1' \
  -H 'cache-control: no-cache'
