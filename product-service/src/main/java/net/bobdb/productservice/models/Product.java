package net.bobdb.productservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    public Product(Product p) {
        this.id = p.id;
        this.name = p.name;
        this.description = p.description;
        this.price = p.price;
    }

    @Id
    private Integer id;
    private String name;
    private String description;
    private String price;
}
