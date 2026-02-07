package com.ecommerce.microservices.inventory.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String productId;
    private String name;
    private boolean active;
}
