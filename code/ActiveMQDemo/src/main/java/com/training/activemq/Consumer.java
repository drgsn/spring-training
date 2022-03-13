package com.training.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Profile("active")
@Component
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @JmsListener(destination = "${spring.activemq.topic}")
    public void receiveTopicMessage(String order) {

        log.info("ActiveMQ consuming message {}", order);
    }




}

