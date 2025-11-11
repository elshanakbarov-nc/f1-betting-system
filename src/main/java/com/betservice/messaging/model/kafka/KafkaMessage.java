package com.betservice.messaging.model.kafka;


import java.time.Instant;

import com.betservice.messaging.model.EventType;
import com.betservice.messaging.model.Message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class KafkaMessage extends Message {
    private String eventId;
    private String driverId;

    public KafkaMessage(
            EventType type,
            Instant createdAt,
            String correlationId) {
        super(type, createdAt, correlationId);
    }

    public KafkaMessage(
            EventType type,
            Instant createdAt,
            String correlationId,
            String driverId,
            String eventId) {
        this(type, createdAt, correlationId);
        this.driverId = driverId;
        this.eventId = eventId;
    }
}

