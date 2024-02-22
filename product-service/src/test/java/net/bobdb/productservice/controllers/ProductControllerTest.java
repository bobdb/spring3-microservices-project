package net.bobdb.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        TEST_DB_PRODUCTS = List.of(
                Product.builder()
                        .id(1)
                        .name("Product Numero Uno")
                        .price("1.23")
                        .description("Something espsecial!")
                        .build(),
                Product.builder()
                        .id(2)
                        .name("Product Numero Dos")
                        .price("4.56")
                        .description("Something also espsecial!")
                        .build()
                );
    }

    @Test
    void findAll() throws Exception {

        List<Product> testProducts = TEST_DB_PRODUCTS;
        when(productService.findAll()).thenReturn(testProducts);

        MvcResult mvcResult = mockMvc.perform(get("/api/products")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mockMvc.perform(get("/api/products"))
        .andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(testProducts))
                );

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testProducts.get(0).getName())));
    }

    @Test
    void shouldFindProductWithValidId() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.of(TEST_DB_PRODUCTS.get(0)));
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
        Product testProduct = TEST_DB_PRODUCTS.get(0);
        testProduct.setId(null);
        when(productService.createProduct(testProduct))
                .thenReturn(testProduct);
        String json = getJsonAsString();
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateProductWhenObjectIsInvalid() throws Exception {
        String json = getInvalidJsonAsString();
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateProductWhenGiveValidPost() throws Exception {
        Product testProduct = TEST_DB_PRODUCTS.get(0);
        when(productService.findById(1)).thenReturn(Optional.of(testProduct));

        Product updatedProduct = new Product(TEST_DB_PRODUCTS.get(0));
        when(productService.updateProduct(updatedProduct)).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdatePostWhenGiveInValidPost() throws Exception {
        String json = getInvalidJsonAsString();
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test void shouldDeleteProductWhenValidId() throws Exception {

        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService,times(1)).deleteProduct(1);
    }

    private String getInvalidJsonAsString() {
        return """
                {
                    "name": "Product Numero Malo",
                    "description": "No espsecial!"
                }
                """;
    }

    private String getJsonAsString() {
        return                 """
                {
                    "name": "Product Numero Uno",
                    "price": "1.23",
                    "description": "Something espsecial!"
                }
                """;
    }

}