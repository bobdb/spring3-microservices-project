package net.bobdb.orderservice.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bobdb.orderservice.dto.OrderRequest;
import net.bobdb.orderservice.mappers.Mapper;
import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import net.bobdb.orderservice.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDTOList()
                                                            .stream()
                                                            .map(mapper::mapToObject)
                                                            .toList();
        Order order = Order.builder()
                .ordernumber(UUID.randomUUID().toString())
                .orderlineitemlist(orderLineItemList)
            .build();

        ordersRepository.save(order);
        log.info("Order number {} placed.",order.getOrdernumber());

    }

}
