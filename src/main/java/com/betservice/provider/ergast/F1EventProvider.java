package com.betservice.provider.ergast;

import java.util.List;
import java.util.Optional;

import com.betservice.rest.dto.EventDTO;

public interface F1EventProvider {
    List<EventDTO> listEvents(Optional<String> sessionType, Optional<String> year, Optional<String> country);
}
