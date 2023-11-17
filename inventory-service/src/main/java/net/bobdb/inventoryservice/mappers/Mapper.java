package net.bobdb.inventoryservice.mappers;

import net.bobdb.inventoryservice.dto.InventoryRequest;
import net.bobdb.inventoryservice.models.Inventory;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public Inventory mapToObject(InventoryRequest inventoryRequest) {
        return Inventory.builder()
                .skucode(inventoryRequest.getSkucode())
                .quantity(inventoryRequest.getQuantity())
                .build();
    }

    public static InventoryRequest mapToDto(Inventory inventory) {
        return InventoryRequest.builder()
                .skucode(inventory.getSkucode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
