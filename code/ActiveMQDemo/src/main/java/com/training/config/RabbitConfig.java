package com.training.config;

import com.training.rabbitmq.Consumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import static org.springframework.jms.support.converter.MessageType.TEXT;


@Profile("rabbit")
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.queue}")
    private String queue;
    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public Queue queue() {
        //For learning purpose - durable=false,
        // in a real project you may need to set this as true.
        return new Queue(queue, true);
    }

    @Bean
    public Exchange fanoutExchange() {
        // durable=true, autoDelete=false
        return new FanoutExchange(exchange, true, false);
    }

    @Bean
    public Binding queueBinding() {
        return new Binding(queue, Binding.DestinationType.QUEUE, exchange, "", null);
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setPort(port);
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        return rabbitTemplate;
//    }
}
