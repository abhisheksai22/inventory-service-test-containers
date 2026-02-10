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

import static com.ecommerce.microservices.inventory.config.RequestIdFilter.REQUEST_ID_HEADER;

@RestController
@RequestMapping("inventory-items")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;


    @PostMapping
    public ResponseEntity<InventoryItemResponseDto> createInventoryItem(@RequestHeader(REQUEST_ID_HEADER) String xRequestId,
                                                                        @RequestBody @Valid InventoryItemRequestDto request) {
        InventoryItem inventoryItem = inventoryItemService.createInventoryItem(request);
        InventoryItemResponseDto response = InventoryItemResponseDto.fromEntity(inventoryItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems(@RequestHeader(REQUEST_ID_HEADER) String xRequestId) {
        List<InventoryItem> inventoryItems = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponseDto> getInventoryItemById(@RequestHeader(REQUEST_ID_HEADER) String xRequestId,
                                                                         @PathVariable Long id) {
        InventoryItem inventoryItem = inventoryItemService.getInventoryItemById(id);
        InventoryItemResponseDto response = InventoryItemResponseDto.fromEntity(inventoryItem);
        return ResponseEntity.ok(response);

    }
}
