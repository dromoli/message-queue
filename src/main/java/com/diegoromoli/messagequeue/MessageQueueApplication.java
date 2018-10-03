package com.diegoromoli.messagequeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class MessageQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueApplication.class, args);
	}
}
