package com.training.activemq;

import com.training.message.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Profile("active")
@Component
public class Producer {

    @Value("${spring.activemq.topic}")
    private String topic;

    private final JmsTemplate jmsTemplate;

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(Order order) {
        log.info("Sending message {} ", order);
        jmsTemplate.convertAndSend(topic, order);
    }
}
