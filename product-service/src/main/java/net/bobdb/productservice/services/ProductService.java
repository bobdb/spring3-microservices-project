package net.bobdb.productservice.services;

import lombok.extern.slf4j.Slf4j;
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(String.valueOf(id));
    }
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(String.valueOf(id));
    }

    public List<Product> findAllWithAIDescription() {
       return productRepository.findAll();
    }
}
