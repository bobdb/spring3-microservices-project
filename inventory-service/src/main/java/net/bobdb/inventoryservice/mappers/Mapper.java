package net.bobdb.inventoryservice.mappers;

import net.bobdb.inventoryservice.dto.InventoryDto;
import net.bobdb.inventoryservice.models.Inventory;
import org.springframework.stereotype.Service;

@Service
public class Mapper {
    public static  Inventory mapToObject(InventoryDto inventoryDto) {
        return Inventory.builder()
                .skucode(inventoryDto.getSkucode())
                .quantity(inventoryDto.getQuantity())
                .build();
    }

    public static InventoryDto mapToDto(Inventory inventory) {
        return InventoryDto.builder()
                .skucode(inventory.getSkucode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
