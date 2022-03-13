package com.training.controller;

import com.training.activemq.Producer;
import com.training.message.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Profile("rabbit")
@RestController
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    private static final Logger log = LoggerFactory.getLogger(RabbitController.class);

    @GetMapping("/message/{name}/{qty}")
    public void sendMessageRabbit(@PathVariable("name") String name, @PathVariable("qty") Integer qty) {
        log.info("Sending message = {} {}", name, qty);
        Order order = new Order(name, qty);
        rabbitTemplate.convertAndSend(queue, order);
    }
}
