package com.diegoromoli.messagequeue;

import com.diegoromoli.messagequeue.controller.MessageQueueRestController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageQueueApplicationTests extends AbstractMessageQueueTest {

    @Autowired
    private MessageQueueRestController messageQueueRestController;

    @Test
	public void contextLoads() {
	}

	@Test
    public void controllerWiredCorrectly() {
	    assertNotNull(messageQueueRestController);
    }

	@Test
    public void sendSimpleMessage() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/message-queue/message").content("This is the test message")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(1L, Long.parseLong(content));
    }

}
