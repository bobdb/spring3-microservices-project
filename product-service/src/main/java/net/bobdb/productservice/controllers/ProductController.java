package net.bobdb.productservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import net.bobdb.productservice.batch.ProductsProcessor;
import net.bobdb.productservice.dto.ProductDTO;

import net.bobdb.productservice.mappers.ProductMapper;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Products", description = "Products API")
@RestController
@RequestMapping("/api/products")
@Slf4j
class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Operation(
            summary = "Get All Products",
            description = "Gets all Products in the database. The response is a list of Product objects.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findAll(@RequestParam(required = false) Optional<Boolean> useAIDescription) {
        if  (useAIDescription.isPresent() && useAIDescription.get())  {
            LOGGER.info("use the AI description");
            return ProductMapper.mapToDTO(productService.findAllWithAIDescription());
        }
        return ProductMapper.mapToDTO(productService.findAll());
    }

    @Operation(
            summary = "Get A Product By Id",
            description = "Gets a Product from the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<Product> findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @Operation(
            summary = "Create Product",
            description = "Inserts a new Product in the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product p = productService.createProduct(ProductMapper.mapToObject(productDTO));
        return ProductMapper.mapToDTO(p);
    }

    @Operation(
            summary = "Modify Product",
            description = "Updates a Product in the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO updateProduct(@PathVariable Integer id, @RequestBody @Valid ProductDTO productDTO) {
        Optional<Product> existingProduct = productService.findById(id);

        if (existingProduct.isEmpty())
            throw new ProductNotFoundException();

        Product updatedProduct = Product.builder()
                    .id(existingProduct.get().getId())
                    .name(productDTO.getName())
                    .manufacturer(productDTO.getManufacturer())
                    .price(productDTO.getPrice())
                    .year(productDTO.getYear())
                    .description(productDTO.getDescription())
                .build();
        var p =  productService.updateProduct(updatedProduct);
        return ProductMapper.mapToDTO(p); // on failure sends 500 in service
    }

    @Operation(
            summary = "Delete Product",
            description = "Deletes a Product from the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }


}
