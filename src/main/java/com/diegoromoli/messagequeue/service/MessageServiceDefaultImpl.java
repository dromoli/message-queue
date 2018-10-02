package com.diegoromoli.messagequeue.service;

import com.diegoromoli.messagequeue.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceDefaultImpl implements MessageService {

    @Override
    public Message storeMessage(String messageString) {
        Message message = new Message(1L, messageString);
        // store message
        return message;
    }
}
