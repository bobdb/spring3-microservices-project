package net.bobdb.orderservice.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bobdb.orderservice.dto.InventoryResponse;
import net.bobdb.orderservice.dto.OrderRequest;
import net.bobdb.orderservice.event.OrderPlacedEvent;
import net.bobdb.orderservice.mappers.Mapper;
import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import net.bobdb.orderservice.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDTOList()
                                                            .stream()
                                                            .map(mapper::mapToObject)
                                                            .toList();
        Order order = Order.builder()
                .ordernumber(UUID.randomUUID().toString())
                .orderlineitemlist(orderLineItemList)
            .build();

        List<String> skucodes = order.getOrderlineitemlist().stream()
                .map(OrderLineItem::getSkucode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
                 .get()
                 .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skucode", skucodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            ordersRepository.save(order);
//            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrdernumber()));
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrdernumber()));
            return "Order Placed Successfully";
        } else {
            throw new IllegalArgumentException("not in stock");
        }

    }

}
