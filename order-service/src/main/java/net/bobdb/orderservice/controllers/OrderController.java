package net.bobdb.orderservice.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.bobdb.orderservice.dto.OrderDTO;
import net.bobdb.orderservice.dto.OrderRequest;
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
    @CircuitBreaker(name="inventory", fallbackMethod = "fallback")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully."; //TODO error, add USER and admin for roles
    }

    public String fallback(OrderRequest orderRequest, RuntimeException runtimeException) {
        return "Something went wrong.  Try again";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK) //TODO make this ADMIN only, prohibit USER
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

}
