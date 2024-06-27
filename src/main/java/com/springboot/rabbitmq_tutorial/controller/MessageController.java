package com.springboot.rabbitmq_tutorial.controller;

import com.springboot.rabbitmq_tutorial.publisher.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final RabbitMQProducer producer;

    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    // Endpoint: http://localhost:8080/rabbitmq/publish?message=hello
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        try {
            producer.sendMessage(message);
            logger.info("Message sent to RabbitMQ: {}", message);
            return ResponseEntity.ok("Message sent to RabbitMQ");
        } catch (Exception e) {
            logger.error("Failed to send message to RabbitMQ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message");
        }
    }
}
