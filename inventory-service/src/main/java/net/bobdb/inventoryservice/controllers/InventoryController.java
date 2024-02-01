package net.bobdb.inventoryservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bobdb.inventoryservice.dto.InventoryDto;
import net.bobdb.inventoryservice.dto.InventoryResponse;
import net.bobdb.inventoryservice.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Inventory", description = "Inventory API")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;
    @Operation(
            summary = "Find Inventory (individually) by skucode(s)",
            description = "Finds inventory in the database.  By default, acts as a findAll. One or more skucodes may be provided " +
                          "as request parameters.  The response will always return a List, even if it's empty. Items with zero" +
                          "inventory will return indicating as such.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryDto.class), mediaType = "application/json") })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> find(@RequestParam(required = false) List<String> skucode) {
        if (skucode==null)
            return inventoryService.findAll();

        return inventoryService.findBySkuCodes(skucode);

    }
    @Operation(
            summary = "Check if products are in the Inventory database and available (quantity > 0)",
            description = "Finds if items(s) are in the database by skucode, provided as request parameters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryResponse.class), mediaType = "application/json") })})
    @GetMapping("/instock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucode) {
        return inventoryService.isInStock(skucode);
    }
    @Operation(
            summary = "Create Inventory",
            description = "Inserts a new product into the Inventory database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = InventoryDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInventory(@RequestBody InventoryDto inventoryDto) {
        inventoryService.createInventory(inventoryDto);
        return "inventory created: " + inventoryDto;
    }

}
