package com.ecommerce.microservices.inventory.service;

import com.ecommerce.microservices.inventory.client.ProductClient;
import com.ecommerce.microservices.inventory.model.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private final ProductClient productClient;

    @Cacheable(
            value = "products",
            key = "#productId",
            unless = "#result == null"
    )
    public ProductResponse getProduct(String productId) {
        return productClient.getProductById(productId);
    }
}
