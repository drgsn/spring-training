package com.example.consumer;

import com.example.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@EnableBinding(Sink.class)
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @StreamListener(Sink.INPUT)
    public void processMessage(Message message) {
        log.info("receive message {}", message);
        log.info("payload = {}", message.getPayload());
    }

//    @StreamListener(Sink.INPUT)
//    public void processMessage(Order order) {
//        log.info("receive message {}", order);
//    }
}
