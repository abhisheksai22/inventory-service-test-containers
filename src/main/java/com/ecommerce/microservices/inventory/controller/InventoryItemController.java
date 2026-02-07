package com.ecommerce.microservices.inventory.controller;

import com.ecommerce.microservices.inventory.entity.InventoryItem;
import com.ecommerce.microservices.inventory.model.InventoryItemRequestDto;
import com.ecommerce.microservices.inventory.model.InventoryItemResponseDto;
import com.ecommerce.microservices.inventory.service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory-items")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;


    @PostMapping
    public ResponseEntity<InventoryItemResponseDto> createInventoryItem(@RequestBody @Valid InventoryItemRequestDto request) {
        InventoryItem inventoryItem = inventoryItemService.createInventoryItem(request);
        InventoryItemResponseDto response = InventoryItemResponseDto.fromEntity(inventoryItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponseDto> getInventoryItemById(@PathVariable Long id) {
        InventoryItem inventoryItem = inventoryItemService.getInventoryItemById(id);
        InventoryItemResponseDto response = InventoryItemResponseDto.fromEntity(inventoryItem);
        return ResponseEntity.ok(response);

    }
}
