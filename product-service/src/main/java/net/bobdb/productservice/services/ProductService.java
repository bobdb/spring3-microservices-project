package net.bobdb.productservice.services;

import lombok.extern.slf4j.Slf4j;
import net.bobdb.productservice.controllers.ProductNotFoundException;
import net.bobdb.productservice.dto.ProductRequest;
import net.bobdb.productservice.dto.ProductResponse;
import net.bobdb.productservice.mappers.ProductMapper;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public
class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(ProductMapper::mapToResponse).toList();
    }

    public Product createProduct(ProductRequest productRequest) {
        log.info("Product {} is saved", productRequest.getName());
        return productRepository.save(ProductMapper.mapToObject(productRequest));

    }

    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(productRepository.findById(String.valueOf(id))
                                                    .orElseThrow(ProductNotFoundException::new));
    }
}
