# spring3-microservices-project

## The Modules So Far
1. product-service - MongoDB
2. inventory-service - Postgres
3. order-service - Postgres
4. discovery-server (default: 8761)
5. API gateway (default: 8080)

## Other available stuff
1. Actuator (on order service: 9001)
2. Zipkin (default :9411/zipkin)

### Changelog
- 1.0.0 basic services running
- 1.1.0 order and inventory talking
- 1.2.0 discovery server and client-side load balancing
- 1.3.0 API gateway, routing
- 1.4.0 Circuitbreaker (Resilience4j)
- 1.5.0 Distributed tracing, Zipkin

