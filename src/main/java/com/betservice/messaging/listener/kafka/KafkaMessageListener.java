package com.betservice.messaging.listener.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.betservice.messaging.consumer.Consumer;
import com.betservice.messaging.listener.MessageListener;
import com.betservice.messaging.model.kafka.KafkaMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KafkaMessageListener implements MessageListener<KafkaMessage> {
    private Consumer consumer;
    @Override
    @KafkaListener(
            topics = "${app.topics.bet.formula.one}",
            groupId = "bet-consumer-group"
    )
    public void onMessage(ConsumerRecord<String, KafkaMessage> record) {
        var message = record.value();
        consumer.consumeMessage(message);
        System.out.println("🍔 Kafka message received: " + message);
    }
}
