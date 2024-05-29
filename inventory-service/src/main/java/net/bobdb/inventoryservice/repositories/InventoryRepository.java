package net.bobdb.inventoryservice.repositories;

import net.bobdb.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findBySkucodeIn(List<String> skuCode);

    Optional<Inventory> findByModelId(Integer modelId);

}
