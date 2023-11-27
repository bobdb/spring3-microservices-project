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

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> findAll() {
        return inventoryService.findAll();
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucode) {
        return inventoryService.isInStock(skucode);
    }

}
