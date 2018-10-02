package com.diegoromoli.messagequeue.service;

import com.diegoromoli.messagequeue.domain.Message;

public interface MessageService {

    Message storeMessage(String messageString);

}
