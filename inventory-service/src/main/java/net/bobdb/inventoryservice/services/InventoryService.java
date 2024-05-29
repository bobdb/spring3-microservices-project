package net.bobdb.inventoryservice.services;

import lombok.extern.slf4j.Slf4j;
import net.bobdb.inventoryservice.dto.InventoryDto;
import net.bobdb.inventoryservice.dto.InventoryResponse;
import net.bobdb.inventoryservice.mappers.Mapper;
import net.bobdb.inventoryservice.models.Inventory;
import net.bobdb.inventoryservice.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    Mapper mapper;

    public List<InventoryDto> findAll() {
        List<Inventory> list = inventoryRepository.findAll();
        return list.stream().map(Mapper::mapToDto).toList();
    }

    public InventoryDto findById(Long id) {
        Optional<Inventory> inventoryObject = inventoryRepository.findById(id);
        return inventoryObject.map(Mapper::mapToDto).orElseThrow();
    }

    public List<InventoryDto> findBySkuCodes(List<String> skucode) {
        List<Inventory> list = inventoryRepository.findBySkucodeIn(skucode);
        return list.stream().map(Mapper::mapToDto).toList();
    }

    public void createInventory(InventoryDto inventoryDto) {
        Inventory inventory = mapper.mapToObject(inventoryDto);
        inventoryRepository.save(inventory);
        log.info("Inventory Item " + inventoryDto.getSkucode() + " created" );
    }
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkucodeIn(skuCode).stream()
                .map(i-> InventoryResponse.builder()
                        .skucode(i.getSkucode())
                        .isInStock(i.getQuantity()>0)
                        .build() )
                .toList();


    }

    public InventoryDto findByModelId(Long id) {
        Optional<Inventory> inventoryObject = inventoryRepository.findByModelId(Math.toIntExact(id));
        return inventoryObject.map(Mapper::mapToDto).orElse(new InventoryDto("",-1, Math.toIntExact(id)));
    }
}
