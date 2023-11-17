package net.bobdb.orderservice.mappers;

import net.bobdb.orderservice.dto.OrderLineItemDTO;
import net.bobdb.orderservice.models.OrderLineItem;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public OrderLineItem mapToObject(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.builder()
                    .skucode(orderLineItemDTO.getSkucode())
                    .price(orderLineItemDTO.getPrice())
                    .quantity(orderLineItemDTO.getQuantity())
                .build();
    }

}
