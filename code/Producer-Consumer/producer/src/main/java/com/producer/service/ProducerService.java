package com.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final static Logger log = LoggerFactory.getLogger(ProducerService.class);

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        log.info("message to be sent is {}", message);
        rabbitTemplate.convertAndSend(queue, message);
    }

}
