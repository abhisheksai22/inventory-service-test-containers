package com.ecommerce.microservices.inventory.service;

import com.ecommerce.microservices.inventory.client.ProductClient;
import com.ecommerce.microservices.inventory.repo.InventoryItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class InventoryItemServiceTest {

    @Mock
    private InventoryItemRepository repository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private InventoryItemService inventoryItemService;

    @Test
    void shouldThrowExceptionWhenProductInactive() {
        // business logic tests here
    }
}
