package net.bobdb.productservice;

import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {

    @Autowired
    ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner loadData(ProductRepository ProductRepository) {
//       List<Product> ProductList = List.of(
//            Product.builder().name("Les Paul Standard").manufacturer("Gibson").year("1960").price("10000.00").build(),
//            Product.builder().name("Flying V").manufacturer("Gibson").year("1972").price("8000.00").build(),
//            Product.builder().name("Stratocaster").manufacturer("Fender").year("1970").price("6000.00").build(),
//            Product.builder().name("Telecaster").manufacturer("Fender").year("1982").price("1000.00").build(),
//            Product.builder().name("CE24").manufacturer("PRS").year("2023").price("2499.99").build()
//        );
//        for (Product i : ProductList) {
//            logger.info(i + "added to ProductRepository");
//        }
        return args -> {
         //   ProductRepository.saveAll(ProductList);
         //   ProductRepository.save(  Product.builder().name("Les Paul Standard").manufacturer("Gibson").year("1960").price("10000.00").build());
        };
    }
}
