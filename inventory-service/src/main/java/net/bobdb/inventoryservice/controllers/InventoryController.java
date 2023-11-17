package net.bobdb.inventoryservice.controllers;

import net.bobdb.inventoryservice.dto.InventoryRequest;
import net.bobdb.inventoryservice.dto.InventoryResponse;
import net.bobdb.inventoryservice.mappers.Mapper;
import net.bobdb.inventoryservice.services.InventoryService;
import net.bobdb.inventoryservice.models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    Mapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryRequest> findAll() {
       List<Inventory> inventoryList = inventoryService.findAll();
       return inventoryList.stream().map(Mapper::mapToDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createInventory(inventoryRequest);
        return InventoryResponse.builder()
                .message("inventory " + inventoryRequest.getSkucode() + " created")
                .build();
    }

    @GetMapping("/{skucode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("skucode") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

}
