package com.training.controller;

import com.training.activemq.Producer;
import com.training.message.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Profile("active")
@RestController
public class ActiveController {

    @Autowired
    private Producer producer;

    private static final Logger log = LoggerFactory.getLogger(ActiveController.class);

    @GetMapping("/message/{name}/{qty}")
    public void sendMessageActive(@PathVariable("name") String name, @PathVariable("qty") Integer qty) {
        log.info("Sending message = {} {}", name, qty);
        Order order = new Order(name, qty);
        producer.sendMessage(order);
    }
}
