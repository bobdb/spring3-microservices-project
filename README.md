# spring3-microservices-project

## The Modules So Far
1. product-service - MongoDB
2. inventory-service - Postgres
3. order-service - Postgres
4. discovery-server (localhost:8761)
5. API gateway (localhost:8080)

## Other available stuff
1. Actuator (located in order-service; localhost:9001)
2. Zipkin (9411/zipkin)
3. Kafka (29092:notificationId:notificationTopic)
4. Keycloak (8181:8080)

## Endpoints
(TODO just add a link to swagger)
1. GET localhost:8080/eureka/web
2. GET localhost:8080/products/ --> findAll()
3. POST localhost:8080/products/ {ProductRequest}
5. GET localhost:8080/inventory/ --> findAll()
6. GET localhost:8080/inventory/?skucode=Strat&skucode=Tele
6. POST localhost:8080/orders {[OrderLineItem]}

### Changelog
- 1.0.0 basic services running
- 1.1.0 order and inventory talking
- 1.2.0 discovery server and client-side load balancing
- 1.3.0 API gateway, routing
- 1.4.0 Circuitbreaker (Resilience4j)
- 1.5.0 Distributed tracing, Zipkin
- 1.6.0 Event-driven notifiation service (Kafka)

