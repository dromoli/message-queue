package com.diegoromoli.messagequeue;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractMessageQueueTest {

    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
