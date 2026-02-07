package com.ecommerce.microservices.inventory.integration;

import com.ecommerce.microservices.inventory.TestcontainersConfiguration;
import com.ecommerce.microservices.inventory.client.ProductClient;
import com.ecommerce.microservices.inventory.model.ProductResponse;
import com.ecommerce.microservices.inventory.repo.InventoryItemRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class InventoryItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @MockitoBean
    private ProductClient productClient;


    @BeforeEach
    void cleanDatabase() {
        inventoryItemRepository.deleteAll();
    }



    @Test
    void fullFlow_createInventory_usesRedisAndPostgres() throws Exception {

        when(productClient.getProductById("P1001"))
                .thenReturn(new ProductResponse("P1001", "Laptop", true));

        String body = """
        {
          "productId": "P1001",
          "availableQuantity": 20
        }
        """;

        // First create → OK
        mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated());

        // Second create → Conflict
        mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isConflict());

        // Cache verified
        verify(productClient, times(1))
                .getProductById("P1001");
    }



    @Test
    void shouldCreateAndFetchInventoryItem() throws Exception {
        when(productClient.getProductById("P1001"))
                .thenReturn(new ProductResponse("P1001", "Phone", true));

        String requestBody = """
            {
              "productId": "P1001",
              "availableQuantity": 50
            }
            """;

        String response = mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");

        mockMvc.perform(get("/inventory-items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

    }

    @Test
    void shouldFailWhenQuantityIsNegative() throws Exception {
        String requestBody = """
        {
          "productId": "P2001",
          "availableQuantity": -5
        }
        """;
        mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFetchInventoryItemById() throws Exception {

        when(productClient.getProductById("P1002"))
                .thenReturn(new ProductResponse("P1002", "Phone", true));

        String requestBody = """
        {
          "productId": "P1002",
          "availableQuantity": 50
        }
        """;

        String response = mockMvc.perform(post("/inventory-items")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        mockMvc.perform(get("/inventory-items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.productId").value("P1002"));
    }



}
