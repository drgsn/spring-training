package com.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Queue queue(){
        return new Queue(queue, true);
    }

    @Bean
    public Exchange exchange(){
        return new FanoutExchange(exchange, true, false);
    }

    @Bean
    public Binding queueBinding() {
        return new Binding(queue, Binding.DestinationType.QUEUE, exchange, "", null);
    }
}
