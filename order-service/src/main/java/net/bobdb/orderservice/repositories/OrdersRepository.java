package net.bobdb.orderservice.repositories;

import net.bobdb.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {

}
