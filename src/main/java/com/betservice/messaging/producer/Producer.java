package com.betservice.messaging.producer;

import com.betservice.messaging.model.Message;

public interface Producer<T extends Message> {
    public void sendMessage(T message);
}
