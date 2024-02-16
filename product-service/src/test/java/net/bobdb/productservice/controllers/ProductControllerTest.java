package net.bobdb.productservice.controllers;

import net.bobdb.productservice.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {

    @Test
    void findAll() {

    }

    @Test
    void createProduct() {
        Product product = Product.builder()
                                    .name("Product Numero Uno")
                                    .price(BigDecimal.valueOf(1.23))
                                    .description("Something espsecial!")
                            .build();
//        JUnit
//        assertEquals("Product Numero Uno", product.getName(), "Product Name was not 'Product Numero Uno'");

        //assertJ
        assertThat(product.getName())
                .startsWith("P")
                .endsWith("o")
                .isEqualTo("Product Numero Uno");


        //Hamcrest
//        Product product2 = Product.builder()
//                .name("Product Numero Uno")
//                .price(BigDecimal.valueOf(1.23))
//                .description("Something espsecial!")
//                .build();
//        assertThat(test1,equalTo(product2));1


    }
}