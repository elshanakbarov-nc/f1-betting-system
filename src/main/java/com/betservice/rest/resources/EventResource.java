package com.betservice.rest.resources;

import com.betservice.rest.dto.EventDTO;
import com.betservice.rest.request.EventOutcomeRequest;
import com.betservice.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventResource {

    private final EventService eventService;

    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(
            @RequestParam Optional<String> sessionType,
            @RequestParam Optional<String> year,
            @RequestParam Optional<String> country
    ) {
        List<EventDTO> events = eventService.listEvents(sessionType, year, country);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/outcome")
    public ResponseEntity<Void> processEventOutcome(@RequestBody EventOutcomeRequest request) {
        eventService.processEventOutcome(request.eventId(), request.winningDriverId());
        return ResponseEntity.accepted().build();
    }
}
