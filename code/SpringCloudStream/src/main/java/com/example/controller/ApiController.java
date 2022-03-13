package com.example.controller;


import com.example.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Source.class)
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private Source source;

    @GetMapping("/")
    public String test(){
        return "Welcome, everything is up";
    }

    @GetMapping("/hello/{message}")
    public String greetings(@PathVariable String message){
        return "Hey, " + message;
    }

    @GetMapping("/message/{name}/{qty}")
    public void sendMessageRabbit(@PathVariable("name") String name, @PathVariable("qty") Integer qty) {
        log.info("Sending message = {} {}", name, qty);
        Order order = new Order(name, qty);
        // publish message to rabbit mq exchange
        Message message = MessageBuilder.withPayload(order)
                // header is not required. is set as a demo
                .setHeader("test-header", "my-test-header").build();
        source.output().send(message);
        log.info("message sent to rabbit mq");
    }
}
