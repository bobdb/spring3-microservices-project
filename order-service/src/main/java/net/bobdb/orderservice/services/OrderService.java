package net.bobdb.orderservice.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bobdb.orderservice.dto.InventoryResponse;
import net.bobdb.orderservice.dto.OrderRequest;
import net.bobdb.orderservice.mappers.Mapper;
import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import net.bobdb.orderservice.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
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

        InventoryResponse[] inventoryResponseArray = webClient.get()
                 .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skucode", skucodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            ordersRepository.save(order);
        } else {
            throw new IllegalArgumentException("not in stock");
        }

    }

}
