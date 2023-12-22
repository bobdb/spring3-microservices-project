package net.bobdb.inventoryservice.controllers;

import net.bobdb.inventoryservice.dto.InventoryDto;
import net.bobdb.inventoryservice.dto.InventoryResponse;
import net.bobdb.inventoryservice.mappers.Mapper;
import net.bobdb.inventoryservice.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> find(@RequestParam(required = false) List<String> skucode) {
        if (skucode==null)
            return inventoryService.findAll();

        return inventoryService.findBySkuCodes(skucode);

    }

    @GetMapping("/instock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucode) {
        return inventoryService.isInStock(skucode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInventory(@RequestBody InventoryDto inventoryDto) {
        inventoryService.createInventory(inventoryDto);
        return "inventory created: " + inventoryDto;
    }

}
