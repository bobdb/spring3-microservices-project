package net.bobdb.inventoryservice;

import net.bobdb.inventoryservice.models.Inventory;
import net.bobdb.inventoryservice.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		List<Inventory> inventoryList = new ArrayList<>();
		for (int i=1;i<=72;i++) {
			inventoryList.add(Inventory.builder().modelId(i)
					.quantity((i>50?0:99))
					.build());

		}

		for (Inventory i : inventoryList) {
			System.out.println(i + "added to InventoryRepository");
		}
		return args -> {
			//throw new RuntimeException("bummer dude.");
			inventoryRepository.saveAll(inventoryList);
		};
	}

}
