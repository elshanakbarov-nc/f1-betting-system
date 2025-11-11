package com.betservice.rest.request;

public record EventOutcomeRequest(
        String eventId,
        String winningDriverId
) {}
