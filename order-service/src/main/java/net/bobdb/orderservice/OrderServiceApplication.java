package net.bobdb.orderservice;

import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import net.bobdb.orderservice.repositories.OrdersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

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
