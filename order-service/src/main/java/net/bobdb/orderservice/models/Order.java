package net.bobdb.orderservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ordernumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderlineitemlist;

}
