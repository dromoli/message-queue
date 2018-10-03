package com.diegoromoli.messagequeue.controller;

import com.diegoromoli.messagequeue.domain.Message;
import com.diegoromoli.messagequeue.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message-queue")
public class MessageQueueRestController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageQueueRestController.class);

    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    @ResponseBody
    public Long receiveMessage(@RequestBody String messageString) {
        LOG.debug("Message received: {}", messageString);
        Message message = messageService.storeMessage(messageString);
        return message.getId();
    }
}
