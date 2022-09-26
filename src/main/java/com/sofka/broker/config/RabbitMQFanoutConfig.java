package com.sofka.broker.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfig {

    @Bean
    Queue room1Queue() {
        return new Queue("room1Queue", false);
    }

    @Bean
    Queue room2Queue() {
        return new Queue("room2Queue", false);
    }

    @Bean
    Queue room3Queue() {
        return new Queue("room3Queue", false);
    }

    @Bean
    Queue room4Queue() {
        return new Queue("room4Queue", false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    Binding room1Binding(Queue room1Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(room1Queue).to(exchange);
    }

    @Bean
    Binding room2Binding(Queue room2Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(room2Queue()).to(exchange);
    }

    @Bean
    Binding room3Binding(Queue room3Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(room3Queue()).to(exchange);
    }

    @Bean
    Binding room4Binding(Queue room4Queue, FanoutExchange exchange) {
        return BindingBuilder.bind(room4Queue()).to(exchange);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
