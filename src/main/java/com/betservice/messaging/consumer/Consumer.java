package com.betservice.messaging.consumer;

import com.betservice.messaging.model.Message;

public interface Consumer {
    public void consumeMessage(Message message);
}
