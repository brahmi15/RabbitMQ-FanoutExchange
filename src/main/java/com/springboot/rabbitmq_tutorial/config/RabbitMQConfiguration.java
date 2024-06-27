package com.springboot.rabbitmq_tutorial.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {


    @Value("${rabbitmq.queue.name}")
    private String queue;

    //spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Value("${rabbitmq.queue2.name}")
    private String queue2;

    //spring bean for rabbitmq queue-2
    @Bean
    public Queue queue2(){
        return new Queue(queue2);
    }

    @Value("${rabbitmq.jsonQueue.name}")
    private String jsonQueue;

    //spring bean for queue (store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }

    @Value("${rabbitmq.exchange.name}")
    private  String exchange;

    //spring bean for rabbitmq exchange
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(exchange);
    }

    //for binding queue with exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    //for binding queue2 with exchange using routing key
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

    //Spring beans required to work with broker are :- ConnectionFactory, RabbitTemplate, RabbitAdmin - these are autoconfigured by spring boot autoconfigured.


    //for binding jsonQueue with exchange using json routing key
    @Bean
    public Binding jsonbinding(){
        return BindingBuilder.bind(jsonQueue()).to(fanoutExchange());
    }

    @Bean
    public MessageConverter converter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
