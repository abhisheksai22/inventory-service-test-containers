package com.ecommerce.microservices.inventory.repo;

import com.ecommerce.microservices.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}