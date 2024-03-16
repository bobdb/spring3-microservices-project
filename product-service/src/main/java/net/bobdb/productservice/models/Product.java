package net.bobdb.productservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(value = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    private BigInteger id;
    private String name;
    private String description;
    private String manufacturer;
    private String year;
    private String price;

}
