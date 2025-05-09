# onlinestore

An online store application built with Jakarta EE that provides e-commerce functionality. This project implements a
RESTful API for managing products, shopping carts, and user accounts. The application features include:

- Product catalog management
- Shopping cart functionality
- User account management
- HATEOAS-compliant REST API
- Swagger/OpenAPI documentation
- Comprehensive test coverage with JaCoCo reports

## Requirements
In order to build the project successfully you should have the following installed and configured on your work station:

1. Java 17
2. Maven 3.x.y where y >=0 and x >= 5

## Build
```
mvn clean install
```
 
## How to Start the Application
Simply execute the following command:
`mvn spring-boot:run`

## Testing
### Generating code coverage report
1. Build the Project
2. Move to Jacoco folder and open ```target/site/jacoco/index.html```
3. ```index.html``` will list the coverage for the complete Project

**Coverage Indicators**
- Red    : Not Covered
- Yellow : Partially Covered
- Green  : Completely Covered
