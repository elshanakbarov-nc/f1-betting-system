package com.betservice.rest.response;

public record PlaceBetResponse(
        Long betId,
        String status,
        String message
) {}
