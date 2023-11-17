package net.bobdb.productservice;

import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication()
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	CommandLineRunner runner(ProductRepository productRepository){
		return args -> {
			Product product = Product.builder().name("junk").description("something that sucks").price(BigDecimal.valueOf(123.45)).build();
			productRepository.insert(product);
		};
	}
}