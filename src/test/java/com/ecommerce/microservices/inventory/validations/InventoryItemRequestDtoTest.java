package com.ecommerce.microservices.inventory.validations;

import com.ecommerce.microservices.inventory.model.InventoryItemRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryItemRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenProductIdIsBlank() {
        InventoryItemRequestDto dto = InventoryItemRequestDto.builder()
                .productId("")
                .availableQuantity(10)
                .build();

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldFailWhenQuantityIsNegative() {
        InventoryItemRequestDto dto = InventoryItemRequestDto.builder()
                .productId("P1001")
                .availableQuantity(-5)
                .build();

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }
}
