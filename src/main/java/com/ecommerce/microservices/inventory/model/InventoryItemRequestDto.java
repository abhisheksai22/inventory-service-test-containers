package com.ecommerce.microservices.inventory.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItemRequestDto {

    @NotBlank(message = "Product ID must not be blank")
    private String productId;

    @NotNull(message = "Available quantity is required")
    @Positive(message = "Available quantity must be a positive number")
    private Integer availableQuantity;

}
