package net.bobdb.inventoryservice;

import net.bobdb.inventoryservice.models.Inventory;
import net.bobdb.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = Inventory.builder().skucode("asdfasdfa").quantity(323342).build();
			Inventory inventory1 = Inventory.builder().skucode("bfga").quantity(30342).build();
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);

		};
	}


}
