# CompassUOL SP Challenge - Microservices Project

## Description

This project is part of the CompassUOL SP Challenge and consists of four microservices:

1. **ms-auth (Authorization Service)**: This microservice handles user authentication and authorization. It provides endpoints for user login, token generation, and user role management.

2. **ms-products (Products Service)**: This microservice manages products data. It provides endpoints for creating, updating, deleting, and retrieving products information.

3. **ms-orders (Orders Service)**: This microservice manages order data. It provides endpoints for creating, updating, deleting, and retrieving order information. It also communicates with the ms-products service to check product availability before processing orders.

4. **api-gateway (Gateway Service)**: This microservice acts as an API gateway for the entire system. It routes incoming requests to the appropriate microservices and handles authentication and authorization using the ms-auth service.

## Technologies

The microservices are built using the following technologies:

- Java
- Spring Boot
- MySQL
- RabbitMQ (for communication between ms-orders and ms-products)
- OpenFeign (for communication between ms-orders and ms-products via REST API, optional if using RabbitMQ for communication)
- Spring Security (for authentication and authorization)
- Other technologies...

## Installation and Setup

1. Clone the repository: `git clone https://github.com/your-username/microservices-project.git`
2. Navigate to the project folder for each microservice: `cd ms-auth`, `cd ms-products`, `cd ms-orders`, `cd api-gateway`
3. Install dependencies: `mvn install` (if using Maven)
4. Configure the database for each microservice: use the sql script to configure the database and run the microservices, the tables will be created automatically.
5. Run the ADMINuser script to add a valid user into auth database.
6. Database User: name: root, password: admin.

## Usage

1. Once all microservices are running, you can access the API through the API Gateway service (api-gateway) at `http://localhost:8080` (or the appropriate URL based on your setup).
2. Use the appropriate endpoints provided by each microservice to perform various actions, such as user login, managing products, and placing orders.

## API Endpoints

1. ms-auth:
   - `POST /api/auth/login`: User login to obtain a JWT token for authentication.
   - `POST /api/auth/register`: Register a new user with the system.

2. ms-products:
   - `GET /api/products`: Get a list of all products.
   - `GET /api/products/{id}`: Get details of a specific product by ID.
   - `POST /api/products`: Create a new product.
   - `PUT /api/products/{id}`: Update details of an existing product by ID.
   - `DELETE /api/products/{id}`: Delete a product by ID.

3. ms-orders:
   - `POST /api/orders`: Create a new order. (It communicates with ms-products to check product availability before processing)
   - `GET /api/orders`: Get a list of all orders.
   - `GET /api/orders/{id}`: Get details of a specific order by ID.
   - `PUT /api/orders/{id}`: Update status of an existing order by ID.
