package net.bobdb.inventoryservice;

import net.bobdb.inventoryservice.models.Inventory;
import net.bobdb.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			List<Inventory> inventoryList = List.of(
					Inventory.builder().skucode("Les Paul").quantity(10).build(),
					Inventory.builder().skucode("Strat").quantity(0).build(),
					Inventory.builder().skucode("Explorer").quantity(5).build());
			inventoryRepository.saveAll(inventoryList);
		};
	}


}
