package com.betservice.rest.dto;

import java.util.List;

public record EventDTO(
        String eventId,
        String name,
        String date,
        String sessionType,
        String country,
        List<DriverDTO> drivers
) {}

