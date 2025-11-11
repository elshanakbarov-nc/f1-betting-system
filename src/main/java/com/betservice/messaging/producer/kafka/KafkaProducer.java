package com.betservice.messaging.producer.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.betservice.messaging.model.kafka.KafkaMessage;
import com.betservice.messaging.producer.Producer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements Producer<KafkaMessage> {
    @Value("${app.topics.bet.formula.one}") private String betsTopic;
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Override
    public void sendMessage(KafkaMessage message) {
        System.out.println("🎉 Sending Kafka message: " + message);
        kafkaTemplate.send(betsTopic, String.valueOf(message.getEventId()), message);
    }
}
