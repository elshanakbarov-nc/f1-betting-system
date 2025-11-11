package com.betservice.rest.response;

public record SimulateEventResponse(
        String eventId,
        String winningDriverId,
        int betsWon,
        int betsLost,
        String message
) {}
