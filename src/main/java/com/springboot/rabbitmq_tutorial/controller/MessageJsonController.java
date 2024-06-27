package com.springboot.rabbitmq_tutorial.controller;

import com.springboot.rabbitmq_tutorial.dto.User;
import com.springboot.rabbitmq_tutorial.publisher.RabbitMQJsonProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq/Json")
public class MessageJsonController {

    private final RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        try {
            jsonProducer.sendJsonMessage(user);
            return ResponseEntity.ok("JSON Message sent to RabbitMQ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send JSON Message");
        }
    }
}
