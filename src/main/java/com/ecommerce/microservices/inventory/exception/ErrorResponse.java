package com.ecommerce.microservices.inventory.exception;

import java.time.OffsetDateTime;

public record ErrorResponse(
        String message,
        int status,
        OffsetDateTime timestamp
) {
}

