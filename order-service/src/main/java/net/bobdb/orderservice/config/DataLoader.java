package net.bobdb.orderservice.config;

import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import net.bobdb.orderservice.repositories.OrdersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(OrdersRepository ordersRepository) {
        return args -> {
            List<Order> inventoryList = List.of(
                    Order.builder()
                            .ordernumber("1")
                            .orderlineitemlist(List.of(OrderLineItem.builder()
                                    .skucode("Strat")
                                    .quantity(1)
                                    .price(BigDecimal.valueOf(999.99))
                                    .build()))
                            .build());
            ordersRepository.saveAll(inventoryList);
        };
    }
}
