package com.ecommerce.microservices.inventory.service;

import com.ecommerce.microservices.inventory.entity.InventoryItem;
import com.ecommerce.microservices.inventory.exception.InventoryNotFoundException;
import com.ecommerce.microservices.inventory.exception.ProductNotFoundException;
import com.ecommerce.microservices.inventory.model.InventoryItemRequestDto;
import com.ecommerce.microservices.inventory.model.ProductResponse;
import com.ecommerce.microservices.inventory.repo.InventoryItemRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ProductCacheService productCacheService;

    @Transactional
    public InventoryItem createInventoryItem(InventoryItemRequestDto request) {

        ProductResponse product;
        try {
            product = productCacheService.getProduct(request.getProductId());
        } catch (FeignException.NotFound ex) {
            throw new ProductNotFoundException(request.getProductId());
        } catch (FeignException ex) {
            throw new IllegalStateException("Product service unavailable");
        }

        if (!product.isActive()) {
            throw new IllegalStateException("Product is inactive");
        }

        InventoryItem inventoryItem = InventoryItem.builder()
                .productId(request.getProductId())
                .availableQuantity(request.getAvailableQuantity())
                .build();

        return inventoryItemRepository.save(inventoryItem);
    }


    @Transactional(readOnly = true)
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public InventoryItem getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory item not found with id: " + id));
    }

}
