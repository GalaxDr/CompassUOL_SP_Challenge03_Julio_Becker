package br.com.compassuol.sp.challenge.msproducts.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String REQUEST_QUEUE = "product-request-queue";
    public static final String RESPONSE_QUEUE = "product-response-queue"; // Adicionado
    public static final String EXCHANGE_NAME = "product-exchange";
    public static final String ROUTING_KEY = "product-request-routing-key";

    @Bean
    Queue requestQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }

    @Bean
    Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE, false); // Adicionado
    }

    @Bean
    DirectExchange requestExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding requestBinding(Queue requestQueue, DirectExchange requestExchange) {
        return BindingBuilder.bind(requestQueue).to(requestExchange).with(ROUTING_KEY);
    }
}