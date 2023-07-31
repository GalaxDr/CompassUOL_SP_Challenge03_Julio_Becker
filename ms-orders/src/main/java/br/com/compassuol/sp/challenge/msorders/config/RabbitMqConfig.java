package br.com.compassuol.sp.challenge.msorders.config;


import br.com.compassuol.sp.challenge.msorders.rabbitmq.ProductRequestProducer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String RESPONSE_QUEUE = "product-response-queue";
    public static final String EXCHANGE_NAME = "product-exchange";
    public static final String ROUTING_KEY = "product-request-routing-key";

    @Bean
    Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder.bind(responseQueue).to(exchange).with(ROUTING_KEY);
    }
}