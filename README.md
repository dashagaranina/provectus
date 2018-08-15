# Provectus task
# Assignment
Implement asynchronous calculation of PI number up to a given number of correct digits.

For calculation of PI please use Gregory-Leibniz formula:
https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80

Please create two different solutions described below.

## Task 1. Single-process solution 

See in [Standalone](https://github.com/dashagaranina/provectus/tree/master/standalone)

## Task 2. Distributed solution
Create `DistributedSolution` application (implements `foo.bar.baz.Solution`) that uses a cluster of three `Worker` applications to calculate the PI number in a distributed manner.
All requests to Workers should be asynchronous, meaning the caller side doesn't wait for a completion of a job. You are free to introduce any intermediary applications on need.

Application should work on Spring Boot 2, save a calculation result and a request statistic (to Master) into database, and should have Rest API for requests to get the statistic. Also preferable to use Docker container platform.

## Realisation
![project schema](https://user-images.githubusercontent.com/7047331/44172527-26e0f780-a0e6-11e8-85d3-adef236a3363.jpg)

Technologies (The application is written using Spring Framework):
- Spring Boot 2 (allows to get an application as quickly as possible)
- Spring Data JPA (makes it easy to easily implement JPA based repositories)
- Netflix Eureka (is used for locating services for the purpose of load balancing and failover of middle-tier servers)
- Netflix Ribbon (client side load balancer)
- Feign Client (allows work with another microservice via simple interface)
- Swagger 2 and Swagger UI (allows you to display the Rest API in a convenient form in a convenient form)
- PostgreSQL (for save data)
- Maven (builds and configures the application)
- Docker (allows deploy and compose services quick and simple)
- JUnit, Mockito, H2  (for testing)

## Eureka service
When every service server (master, worker instances) are successfully uploaded, they are register in the Eureka service. And if a service need to request to another service, it shouldn't know about host and ports of this service. It just ask the Eureka and it coordinates them.

## Master service
The service gets a request with parameter - accuracy (number of correct digits). Using this param, the service can calculate Pi number up to a given accuracy. `DistributedSolution` service uses the accuracy to produce an iteration to `Worker` cluster of services and gets, gathers and saves the calculation result into `Result` database entity.

A response of the request is a result identificator (ID). Someone can gets the result of the calculation by the ID and he gets a json like this:

`{
  "id": "integer",
  "result": "string",
  "accuracy": "integer",
  "timeSpend": "integer"
}`
 
That means: ID - the result identificator, result = calculated Pi, accuracy - requested accuracy, timeSpend - a milliseconds which were spent for calculating.
 
`Master` service uses `Feign Client` and `Ribbon` for conversation with `Worker` service instances. `Feign Client` gives a simple interface for requests without using the `RestTemplate` object. `Ribbon` gets a request to `Worker` service, goes to `Eureka`, asks it about `Worker` instances and locations, and when makes a request to less loaded `Worker` server. 

When `Worker` is done and responses, `Master` gets a result in `CompletableFuture` object. When `DistributedSolution` gathers all `CompletableFutures` together using asynchronous callback methods or handle an exception. After that the calculation result saves. When Pi is still calculating and `Master` gets a request to display a result, it responces with JSON where `result` is null:

`{
  "id": 1,
  "result": null,
  "accuracy": 5",
  "timeSpend": 0"
}`

`Master` also allows you to get requests and results statistics. It has methods to count and get all results, to get a list of results by an accuracy, to get Top 5 fastest and slowest results of Pi calculations.

## Worker service
The service returns the intermediate sums of the Gregory-Leibniz series on a given interval. `Worker` calculates the sums asynchronous also using `CompletableFuture` object features.

## Tests
All services have unit or/and integration tests. You can run them from IDE or using `mvn test` command from a terminal or run .scripts/build_tests.sh (for build the project without test skipping) or .scripts/starts_tests.sh (for build and run docker composition after all tests pass)

## Build, deploy and run
For build, deploy and run the applicatoins you need to install Apache Maven (to the path variables too) and the Docker platform.
You can find all necessary scripts to build and run docker containers in the [scripts](https://github.com/dashagaranina/provectus/tree/master/scripts). Run .sh file from the project root.

Check a system status, an information about instances and etc. from [Eureka web interface](http://localhost:8761/) 

Make requests to the application from [Swagger UI](http://localhost:8080/swagger-ui.html/)
  