# spring3-microservices-project

## The Modules So Far
1. product-service - MongoDB
2. inventory-service - Postgres
3. order-service - Postgres
4. discovery-server (localhost:8761)
5. api-gateway (localhost:8080)
6. notification-service

## Other available stuff
1. Actuator (located in order-service; localhost:9001)
2. Zipkin (9411/zipkin)
3. Kafka (29092:notificationId:notificationTopic)
4. Keycloak (8181:8080)
5. Eureka Discovery Server UI http://localhost:8080/eureka/web

## Endpoints
(TODO just add a link to swagger)

Orders 
- GET /orders --> findAll()
- POST /orders {[OrderLineItem]}

Products
- GET /products --> findAll()
- POST /products {ProductRequest}

Inventory 
- GET /inventory --> findAll()
- GET /inventory?skucode={1}?skucode={2} //TODO change this yuck
- GET /inventory/instock?skucode={1}?skucode={3} // change this too  remember add tests
- POST /inventory/{InventoryDTO}

### Changelog
- 1.0.0 basic services running
- 1.1.0 order and inventory talking
- 1.2.0 discovery server and client-side load balancing
- 1.3.0 API gateway, routing
- 1.4.0 Circuitbreaker (Resilience4j)
- 1.5.0 Distributed tracing, Zipkin
- 1.6.0 Event-driven notification service (Kafka)
- 1.7.0 All sorts of fixes...just look at git 

