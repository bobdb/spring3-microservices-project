package net.bobdb.inventoryservice;

import net.bobdb.inventoryservice.models.Inventory;
import net.bobdb.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory1 = Inventory.builder().skucode("ABC").quantity(10).build();
			Inventory inventory2 = Inventory.builder().skucode("XYZ").quantity(0).build();
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}


}
