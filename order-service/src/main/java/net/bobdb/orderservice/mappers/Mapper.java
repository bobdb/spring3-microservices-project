package net.bobdb.orderservice.mappers;

import net.bobdb.orderservice.dto.OrderDTO;
import net.bobdb.orderservice.dto.OrderLineItemDTO;
import net.bobdb.orderservice.models.Order;
import net.bobdb.orderservice.models.OrderLineItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper {

    public OrderLineItem mapToObject(OrderLineItemDTO orderLineItemDTO) {
        return OrderLineItem.builder()
                    .skucode(orderLineItemDTO.getSkucode())
                    .price(orderLineItemDTO.getPrice())
                    .quantity(orderLineItemDTO.getQuantity())
                .build();
    }

    public List<OrderDTO> mapToDtoList(List<Order> list) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order o : list) {
            orderDTOList.add(OrderDTO.builder().ordernumber(o.getOrdernumber())
                    .orderLineItemDTOList(mapToOliDto(o.getOrderlineitemlist()))
                            .build());
        }
        return orderDTOList;
    }

    private List<OrderLineItemDTO> mapToOliDto(List<OrderLineItem> orderlineitemlist) {
        List<OrderLineItemDTO> list = new ArrayList<>();
        for (OrderLineItem o : orderlineitemlist) {
            list.add(OrderLineItemDTO.builder()
                    .skucode(o.getSkucode())
                    .price(o.getPrice())
                    .quantity(o.getQuantity())
                    .build());
        }
        return list;
    }


}
