package com.ecommerce.microservices.inventory.model;

import com.ecommerce.microservices.inventory.entity.InventoryItem;

public record InventoryItemResponseDto(Long id,
                                       String productId,
                                       Integer availableQuantity) {
    public static InventoryItemResponseDto fromEntity(InventoryItem inventoryItem) {
        return new InventoryItemResponseDto(
                inventoryItem.getId(),
                inventoryItem.getProductId(),
                inventoryItem.getAvailableQuantity()
        );
    }
}
