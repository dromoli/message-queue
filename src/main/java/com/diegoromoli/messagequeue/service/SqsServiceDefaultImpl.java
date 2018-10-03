package com.diegoromoli.messagequeue.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqsServiceDefaultImpl implements SqsService {

    private static final Logger LOG = LoggerFactory.getLogger(SqsServiceDefaultImpl.class);

    @Autowired
    private AmazonSQSAsync sqs;

    @Override
    public void sendMessage(String queue, String message) {
        String queueUrl = sqs.getQueueUrl(queue).getQueueUrl();
        LOG.info("Queue URL: {}", queueUrl);
        SendMessageRequest request = new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(message);
        sqs.sendMessage(request);
    }

    @Override
    public Message receiveMessage(String queue) {
        String queueUrl = sqs.getQueueUrl(queue).getQueueUrl();
        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl).withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(1);
        return sqs.receiveMessage(request).getMessages().get(0);
    }
}
