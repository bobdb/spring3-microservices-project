package net.bobdb.productservice.config;

import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(ProductRepository productRepository) {

        return args -> {
            productRepository.deleteAll();
            List<Product> productList = testProductsFromList();
            productRepository.saveAll(productList);
        };
    }

    private List<Product> testProductsFromList() {
        return List.of(
                Product.builder()
                        .id(BigInteger.ONE)
                        .name("Bobsteenstar")
                        .manufacturer("BobCo")
                        .description("Something really really fresh")
                        .year("2024")
                        .price("10000.00")
                .build());
    }
}
