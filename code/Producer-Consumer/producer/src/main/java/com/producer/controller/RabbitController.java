package com.producer.controller;


import com.producer.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    private static Logger log = LoggerFactory.getLogger(RabbitController.class);

    public final ProducerService producerService;

    @Autowired
    public RabbitController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/message/{message}")
    public void greetings(@PathVariable("message") String message) {
        log.info("message {}", message);
        producerService.sendMessage(message);
    }

}
