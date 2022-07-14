# Order / Product Management REST API

## INTRODUCTION

This application provides a set of endpoints to manage a database of products and orders. To start this web service, install [Maven](https://maven.apache.org/install.html) and execute the following command

    mvn spring-boot:run

Run tests with

    mvn test

## REST ENDPOINTS

The following REST endpoints are available upon deployment of the order management system:

| HTTP Verb        | URL                                  | Description                                                                                                                                                                      | Status Codes                                                                                                                                                                                                            |
| ------------- |--------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `GET` | `http://localhost:8080/product`      | Obtains a list of all existing products                                                                                                                                          | <ul><li>`200 OK`</li></ul>                                                                                                                                                                                              |
| `GET` | `http://localhost:8080/product/{id}` | Obtains the product corresponding to the supplied product ID                                                                                                                     | <ul><li>`200 OK` if order exists</li><li>`404 Not Found` if product does not exist</li></ul>                                                                                                                            |
| `POST` | `http://localhost:8080/product`      | Creates a new product based on the payload contained in the request body, such as:<br />`{ "name": "Fettucini", "availableQuantity": "6", "price": "60" }`                       | <ul><li>`201 Created` if product successfully created</li><li>`400 Bad Request` if parameters are invalid</li></ul>                                                                                                     |
| `PUT` | `http://localhost:8080/product/{id}` | Updates an existing product with the data contained in the request body, same as in the former endpoint:<br />`{ "name": "Fettucini", "availableQuantity": "6", "price": "60" }` | <ul><li>`200 OK` if product succesfully updated</li><li>`400 Bad Request` if parameters are invalid</li><li>`404 Not Found` if product does not exist</li></ul>                                                         |
| `DELETE` | `http://localhost:8080/product/{id}` | Deletes an existing product that corresponds to the supplied product ID                                                                                                          | <ul><li>`204 No Content` if product succesfully deleted</li><li>`404 Not Found` if product does not exist</li></ul>                                                                                                     |
| `GET` | `http://localhost:8080/order`        | Obtains a list of all existing orders                                                                                                                                            | <ul><li>`200 OK`</li></ul>                                                                                                                                                                                              |
| `GET` | `http://localhost:8080/order/{id}`   | Obtains the order corresponding to the supplied order ID                                                                                                                         | <ul><li>`200 OK` if order exists</li><li>`404 Not Found` if order does not exist</li></ul>                                                                                                                              |
| `POST` | `http://localhost:8080/order`        | Creates a new order based on the payload contained in the request body, such as:<br />`{ "orderItems": [{ "id": 1, "quantity": 10 }, { "id": 2, "quantity": 20 }]}`              | <ul><li>`201 Created` if order successfully created</li><li>`400 Bad Request` if parameters are invalid or not enough product stock to create order</li><li>`404 Not Found` if some product ID does not exist</li></ul> |
| `PUT` | `http://localhost:8080/order/{id}`   | Updates an existing order status to PAID or CANCELLED with the status contained in the request body, such as:<br />`{ "orderStatus": "CANCELLED" }`                              | <ul><li>`200 OK` if order succesfully updated</li><li>`400 Bad Request` if parameters or state transition is invalid</li><li>`404 Not Found` if order does not exist</li></ul>                                          |

## ARCHITECTURE

### Domain
- Basic objects to be maintained by the application
- IDs are long integers
- Prices are integers, they are assumed to be in cents to avoid rounding errors

### Application
 - Use cases that contain the business logic and objects required by them, such as repositories & exceptions
 - Any other objects (controllers, schedulers...) interact with the system strictly through use cases
 - It has been assumed that an order can only transition from created to paid or cancelled, and there is no coming back

### Infrastructure
 - Entities that grant access to the application operations
 - Controllers to expose the API, schedulers to execute background tasks, and some input request too
 - Schedulers take care of populating the system with products on start up, and also scan the database periodically for abandoned orders

### Test
- Test are written with Gherkin
- They test the business logic through the use cases
- Each scenario checks the calls to external services / repositories and also the returned and provided values when possible

## Miscelanea

You can inspect the H2 in-memory database (no password required) at

    http://localhost:8080/h2-console

Please find the Swagger documentation at

    http://localhost:8080/swagger-ui/index.html
