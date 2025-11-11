package com.betservice.messaging.consumer.kafka;

import org.springframework.stereotype.Component;

import com.betservice.messaging.consumer.Consumer;
import com.betservice.messaging.model.Message;
import com.betservice.messaging.model.kafka.KafkaMessage;
import com.betservice.service.EventService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BetPlacedConsumer implements Consumer {
    private final EventService service;
    @Override
    public void consumeMessage(Message message) {
        System.out.println("Got message: " + message);
        if (message instanceof KafkaMessage kafkaMessage) {
            System.out.println("Topic = " + kafkaMessage.getEventId());
            service.resolveEventOutcome(kafkaMessage.getEventId(), kafkaMessage.getDriverId());
        } else {
            System.out.println("Invalid message type: " + message.getClass().getName());
        }
    }
}
