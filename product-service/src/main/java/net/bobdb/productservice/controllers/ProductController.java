package net.bobdb.productservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import net.bobdb.productservice.dto.ProductRequest;
import net.bobdb.productservice.dto.ProductResponse;

import net.bobdb.productservice.mappers.ProductMapper;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Products", description = "Products API")
@RestController
@RequestMapping("/api/products")
@Slf4j
class ProductController {

    @Autowired
    ProductService productService;

    @Operation(
            summary = "Get All Products",
            description = "Gets all Products in the database. The response is a list of Product objects, each containing FIX THIS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAll() {
        return productService.findAll();
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
    ProductResponse createProduct(@RequestBody @Validated ProductRequest productRequest) {
        return ProductMapper.mapToResponse(productService.createProduct(productRequest)); // on failure sends 500 in service
    }

}
