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

        return args -> {

        };
    }
}
