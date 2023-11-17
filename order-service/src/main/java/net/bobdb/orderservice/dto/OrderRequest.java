package net.bobdb.orderservice.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import net.bobdb.orderservice.models.OrderLineItem;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderLineItemDTO> orderLineItemDTOList;
}
