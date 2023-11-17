package net.bobdb.productservice.services;


import lombok.extern.slf4j.Slf4j;
import net.bobdb.productservice.dto.ProductRequest;
import net.bobdb.productservice.dto.ProductResponse;
import net.bobdb.productservice.mappers.ProductMapper;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductResponse> findAll() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(ProductMapper::mapToResponse).toList();
    }

    public void createProduct(ProductRequest productRequest) {
        productRepository.save(ProductMapper.mapToObject(productRequest));
        log.info("Product {} is saved", productRequest.getName());
    }
}
