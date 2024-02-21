package net.bobdb.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bobdb.productservice.dto.ProductDTO;
import net.bobdb.productservice.mappers.ProductMapper;
import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import net.bobdb.productservice.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    private List<Product> TEST_DB_PRODUCTS;

    @BeforeEach
    void setUp() {
        TEST_DB_PRODUCTS = List.of(Product.builder()
                .id("1")
               .name("Product Numero Uno")
               .price(BigDecimal.valueOf(1.23))
               .description("Something espsecial!")
               .build(),
               Product.builder()
                       .name("Product Numero Dos")
                       .price(BigDecimal.valueOf(4.56))
                       .description("Something also espsecial!")
                       .build()
                );
    }

    @Test
    void findAll() throws Exception {

        List<Product> testProducts = TEST_DB_PRODUCTS;
        List<ProductDTO> testProductDTOs = ProductMapper.mapToDTO(testProducts);
        when(productService.findAll()).thenReturn(testProductDTOs);

        MvcResult mvcResult = mockMvc.perform(get("/api/products")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mockMvc.perform(get("/api/products"))
        .andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(testProductDTOs))
                );

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testProductDTOs.get(0).getName())));
    }

    @Test
    void shouldFindProductWithValidId() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.of(testProducts.get(0)));
        String json = getJsonAsString();
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
    @Test
    void shouldNotFindProductWithInvalidId() throws Exception {
        when(productService.findById(999)).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateProductWhenObjectIsValid() throws Exception {
        Product product = testProducts.get(0);
        when(productService.createProduct(ProductMapper.mapToDTO(product)))
                .thenReturn(product);
        String json = getJsonAsString();
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateProductWhenObjectIsInvalid() throws Exception {
//        Product product = testProducts.get(0);
//        when(productService.createProduct(ProductMapper.mapToRequest(product))).thenReturn(product);
//        String json = """
//                {
//                    "name": "Product Numero Uno",
//                    "description": "Something espsecial!"
//                }
//                """;
//        mockMvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
    }

    private String getJsonAsString() {
        return                 """
                {
                    "name": "Product Numero Uno",
                    "price": 1.23,
                    "description": "Something espsecial!"
                }
                """;
    }
    List<Product> testProducts = new ArrayList<>();


}