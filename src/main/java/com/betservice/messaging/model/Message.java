package com.betservice.messaging.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private EventType type;
    private Instant createdAt;
    private String correlationId;
}
