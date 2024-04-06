package net.bobdb.productservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.*;
import java.math.BigInteger;
import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private ApplicationContext ctx;


    @Value("${myapp.dbname}")
    private String DEFAULT_DB_DATA;

    @Bean
    CommandLineRunner loadData(ProductRepository productRepository) {

        return args -> {
            productRepository.deleteAll();
         //   List<Product> productList = testProductsFromList();
         //   List<Product> productList = testProductsFromCSVLocal(DEFAULT_DB_DATA);
            List<Product> productList = testProductsFromJsonLocal("products.json");
            productRepository.saveAll(productList);
        };
    }

    private List<Product> testProductsFromJsonLocal(String filename) throws IOException {
        Resource resource = ctx.getResource("classpath:" + filename);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(  resource.getFile(), new TypeReference<List<Product>>(){});
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

    private List<Product> testProductsFromCSVLocal(String filename) throws IOException {

        Resource resource = ctx.getResource("classpath:" + filename);
        return new CsvToBeanBuilder<Product>(new FileReader(resource.getFile()))
                .withType(Product.class).build().parse();

    }
}
