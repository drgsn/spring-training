package com.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @PostConstruct
    public void init(){
        log.info("we are listening to {}", queue);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void readMessage(String message){
        log.info("the message received is {}", message);
    }

}
