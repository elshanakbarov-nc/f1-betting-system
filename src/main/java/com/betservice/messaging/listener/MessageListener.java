package com.betservice.messaging.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.betservice.messaging.model.Message;

public interface MessageListener<T extends Message> {
    void onMessage(ConsumerRecord<String, T> record);
}
