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
            summary = "Find Inventory by Skucode(s)",
            description = "Finds inventory in the db by SkuCode, provided as request parameters(s)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> find(@RequestParam(required = false) List<String> skucode) {
        if (skucode==null)
            return inventoryService.findAll();

        return inventoryService.findBySkuCodes(skucode);

    }
    @Operation(
            summary = "Check if product is in Inventory by Skucode",
            description = "Finds inventory in the db by SkuCode, provided as request parameters(s)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/instock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skucode) {
        return inventoryService.isInStock(skucode);
    }
    @Operation(
            summary = "Create Inventory",
            description = "Inserts a new product into the Inventory db.")
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
