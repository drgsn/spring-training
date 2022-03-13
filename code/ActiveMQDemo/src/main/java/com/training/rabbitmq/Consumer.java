package com.training.rabbitmq;

import com.training.message.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    /**
     * Assigns a Consumer to receive the messages whenever there is one.
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(Order order) {
        log.info("RabbitMQ consuming message - {}", order);
    }

}
