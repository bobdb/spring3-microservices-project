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

import java.math.BigInteger;
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
            Product.builder().id(BigInteger.valueOf(1)).name("Les Paul").manufacturer("Gibson").year("1960").price("10000.00").build(),
            Product.builder().id(BigInteger.valueOf(2)).name("Flying V").manufacturer("Gibson").year("1972").price("8000.00").build(),
            Product.builder().id(BigInteger.valueOf(3)).name("Stratocaster").manufacturer("Fender").year("1970").price("6000.00").build(),
            Product.builder().id(BigInteger.valueOf(4)).name("Telecaster").manufacturer("Fender").year("1982").price("1000.00").build(),
            Product.builder().id(BigInteger.valueOf(5)).name("CE24").manufacturer("PRS").year("2023").price("2499.99").build()
        );
    }

    private static final String TEST_PRODUCT_1_AS_STRING =
                 """
                {
                    "name": "Les Paul",
                    "manufacturer": "Gibson",
                    "year": "1960",
                    "price": "10000.00"
                }
                """;

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
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is(testProducts.get(0).getName())));
    }

    @Test
    void shouldFindAllWithDescriptionsWhenAIFlagEnabled() throws Exception {

        List<Product> testProducts = TEST_DB_PRODUCTS;
        when(productService.findAll()).thenReturn(testProducts);

        MvcResult mvcResult = mockMvc.perform(get("/api/products?useAIDescription=true")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mockMvc.perform(get("/api/products?useAIDescription=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].description").doesNotExist());
        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    void shouldFindProductWithValidId() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.of(TEST_DB_PRODUCTS.get(0)));
        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TEST_PRODUCT_1_AS_STRING));
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
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_PRODUCT_1_AS_STRING))
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
    void shouldUpdateProductWhenGiveValidProduct() throws Exception {
        Product testProduct = TEST_DB_PRODUCTS.get(0);
        when(productService.findById(1)).thenReturn(Optional.of(testProduct)); // existing product
        Product updatedProduct = TEST_DB_PRODUCTS.get(0);
        updatedProduct.setDescription("New Description");
        when(productService.updateProduct(updatedProduct)).thenReturn(updatedProduct);
        mockMvc.perform(put("/api/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdatePostWhenGiveInValidProduct() throws Exception {
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
                    "name": "Bad Product",
                    "description": "Bad Product Description"
                }
                """;
    }



}