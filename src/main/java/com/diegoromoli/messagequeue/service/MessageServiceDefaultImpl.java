package com.diegoromoli.messagequeue.service;

import com.diegoromoli.messagequeue.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceDefaultImpl implements MessageService {

    @Autowired
    private SqsService sqsService;

    @Value("${aws.config.sqs.queue.name}")
    private String sqsQueueName;

    @Override
    public Message storeMessage(String messageString) {
        Message message = new Message(1L, messageString);
        // store message
        sqsService.sendMessage(sqsQueueName, messageString);
        return message;
    }
}
