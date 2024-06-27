package com.springboot.rabbitmq_tutorial.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate, @Value("${rabbitmq.exchange.name}") String exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessage(String message) {
        try {
            logger.info("Message sent -> {}", message);
            rabbitTemplate.convertAndSend(exchange, message);
            logger.info("Message successfully sent to RabbitMQ");
        } catch (Exception e) {
            logger.error("Failed to send message: {}", message, e);
            // Handle or rethrow exception as needed
        }
    }
}
