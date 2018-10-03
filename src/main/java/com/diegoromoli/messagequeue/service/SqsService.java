package com.diegoromoli.messagequeue.service;

import com.amazonaws.services.sqs.model.Message;

public interface SqsService {

    void sendMessage(String queue, String message);

    Message receiveMessage(String queue);

}
