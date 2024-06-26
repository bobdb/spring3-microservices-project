package net.bobdb.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private BigInteger id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
    @NotBlank(message = "Price is mandatory")
    private String price;
    @NotBlank(message = "Year is mandatory")
    private String year;
    @NotBlank(message = "Manufacturer is mandatory")
    private String manufacturer;

}

