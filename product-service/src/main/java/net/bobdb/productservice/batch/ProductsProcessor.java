package net.bobdb.productservice.batch;

import net.bobdb.productservice.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductsProcessor implements ItemProcessor<Product, Product> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsProcessor.class);

    @Override
    public Product process(Product product) {
        LOGGER.info("Processor acting on {}", product);
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .year(product.getYear())
                .description("no description yet")
                .price(product.getDescription())
        .build();
    }
}
