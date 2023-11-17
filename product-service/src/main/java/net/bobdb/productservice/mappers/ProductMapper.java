package net.bobdb.productservice.mappers;

import net.bobdb.productservice.dto.ProductRequest;
import net.bobdb.productservice.dto.ProductResponse;
import net.bobdb.productservice.models.Product;

public class ProductMapper {

    public static Product mapToObject(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }

    public static ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

}
