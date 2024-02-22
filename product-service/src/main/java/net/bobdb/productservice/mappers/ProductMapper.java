package net.bobdb.productservice.mappers;

import net.bobdb.productservice.dto.ProductDTO;
import net.bobdb.productservice.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product mapToObject(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();
    }
    public static ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public static List<ProductDTO> mapToDTO(List<Product> product) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product p : product)
            productDTOs.add(mapToDTO(p));
        return productDTOs;
    }

}
