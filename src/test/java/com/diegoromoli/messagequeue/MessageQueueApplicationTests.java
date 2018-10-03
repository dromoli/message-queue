package com.diegoromoli.messagequeue;

import com.amazonaws.services.sqs.model.Message;
import com.diegoromoli.messagequeue.controller.MessageQueueRestController;
import com.diegoromoli.messagequeue.service.SqsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageQueueApplicationTests extends AbstractMessageQueueTest {

    @Autowired
    private MessageQueueRestController messageQueueRestController;
    @Autowired
    private SqsService sqsService;

    @Value("${aws.config.sqs.queue.name}")
    private String sqsQueueName;

    private static final String MESSAGE_TEXT = "This is the test message";

    @Test
	public void contextLoads() {
	}

	@Test
    public void controllerWiredCorrectly() {
	    assertNotNull(messageQueueRestController);
    }

    @Test
    public void sendSimpleMessage() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/message-queue/message").content(MESSAGE_TEXT)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(1L, Long.parseLong(content));
        Message message = sqsService.receiveMessage(sqsQueueName);
        assertNotNull(message);
        assertEquals(MESSAGE_TEXT, message.getBody());
    }
}
