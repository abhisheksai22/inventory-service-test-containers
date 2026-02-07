package com.ecommerce.microservices.inventory.controller;

import com.ecommerce.microservices.inventory.TestcontainersConfiguration;
import com.ecommerce.microservices.inventory.client.ProductClient;
import com.ecommerce.microservices.inventory.service.InventoryItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryItemController.class)
@Import({TestcontainersConfiguration.class})
class InventoryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryItemService inventoryItemService;

    @MockitoBean
    private ProductClient productClient;

    @Test
    void shouldFailValidation_whenQuantityNegative() throws Exception {
        String body = """
        {
          "productId": "P1001",
          "availableQuantity": -1
        }
        """;

        mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
