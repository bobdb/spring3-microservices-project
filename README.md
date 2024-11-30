# spring3-microservices-project

## The Modules So Far
1. product-service - MongoDB
2. inventory-service - Postgres
3. order-service - Postgres
4. notification-service
5. discovery-server (localhost:8761)
6. api-gateway (localhost:8080)

## Other available stuff
1. Actuator (located in order-service; localhost:9001)
2. Zipkin (9411/zipkin)
3. Kafka (29092:notificationId:notificationTopic)
4. Keycloak (8181:8080)
5. Eureka Discovery Server UI http://localhost:8080/eureka/web

## API Docs - All microservices can be accessed through here!
- http://localhost:8080/swagger-ui.html
- http://localhost:8080/v3/api-docs

### Changelog
- 1.0.0 basic services running
- 1.1.0 order and inventory talking
- 1.2.0 discovery server and client-side load balancing
- 1.3.0 API gateway, routing
- 1.4.0 Circuitbreaker (Resilience4j)
- 1.5.0 Distributed tracing, Zipkin
- 1.6.0 Event-driven notification service (Kafka)
- 1.7.0 All sorts of fixes...just look at git
- 1.8.0 OpenAPI and Swagger support

hello