package com.betservice.rest.request;

import java.math.BigDecimal;

public record PlaceBetRequest(
        Long userId,
        String eventId,
        String driverId,
        BigDecimal amount
) {}
