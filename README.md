# Homework
Homework 3.0

## Content

* Run the tests: `./mvnw clean verify` or run the script `./run-tests`

* The following commands are provided to run the applications with`docker-compose`:

Operation | Command 
--------- | ----------
Start:    | `./start`
Stop:     | `./stop`
Restart:  | `./restart`

* In case you don't have `docker` installed in your PC, you can use the following commands(an in-memory H2 DB will be used):
```
.
├── auth
|   Default Port: 9180
|   To start use: mvn spring-boot:run
├── customer
|   Default Port: 9280
|   To start use: mvn spring-boot:run
├── eureka
|   Default Port: 8761
|   To start use: mvn spring-boot:run
├── gateway
|   Default Port: 9080
|   To start use: mvn spring-boot:run
├── loan
    Default Port: 9380
    To start use: mvn spring-boot:run
```

* customer:
    * [actuator](http://localhost:9280/actuator)
    * [health check](http://localhost:9280/actuator/health)
    * [API docs](http://localhost:9280/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)
* loan:
    * [actuator](http://localhost:9380/actuator)
    * [health check](http://localhost:9380/actuator/health)
    * [API docs](http://localhost:9380/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

Once all the services are up and running please open the [eureka](http://localhost:8761/) dashboard page to check their availability.

After starting the services, you can start testing them either using the postman collection provided, or OpenAPI docs of each service. 

The following endpoints are exposed to simulate a loan creation and search:

No. | Operation                            | url 
--- | ------------------------------------ | ---
1)  | Register                             | http://localhost:9180/register
2)  | Get Oauth Access Token               | http://localhost:9180/oauth/token
3)  | Get User                             | http://localhost:9180/api/users/{userId}
4)  | Create Customer                      | http://localhost:9280/api/customers
5)  | Get Customer                         | http://localhost:9280/api/customers/{customerId}
6)  | Get Customers by IDs                 | http://localhost:9280/api/customers?id=1,2,3,4
7)  | Create Loan Application              | http://localhost:9380/api/loanapplications
8)  | Get Loan Applications By CustomerId  | http://localhost:9380/api/loanapplications?customerId={customerId}
9)  | Search Loan Applications             | http://localhost:9380/search?page=0&size=10&sort=amount,ASC&criteria=amount>=1000,amount<=2000,status:CREATED

### Final note
Please feel free to contact me sending an [email](mailto:led.spaho@gmail.com) for any discussion/doubt/clarification.
