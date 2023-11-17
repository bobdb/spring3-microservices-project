package net.bobdb.orderservice.controllers;

import net.bobdb.orderservice.dto.OrderLineItemDTO;
import net.bobdb.orderservice.dto.OrderRequest;
import net.bobdb.orderservice.dto.OrderResponse;
import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return OrderResponse.builder()
                .message("success. ' placed")
            .build();
    }


}
