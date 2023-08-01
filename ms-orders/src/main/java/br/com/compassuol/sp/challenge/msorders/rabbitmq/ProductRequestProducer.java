package br.com.compassuol.sp.challenge.msorders.rabbitmq;


import br.com.compassuol.sp.challenge.msorders.config.RabbitMqConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public class ProductRequestProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String EXCHANGE_NAME = "product-exchange";
    private static final String REQUEST_ROUTING_KEY = "product-request-routing-key";

    public ProductRequestProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Long> requestProducts(List<Long> productIds, CorrelationData correlationData) {
        String responseQueue = RabbitMqConfig.RESPONSE_QUEUE;

        byte[] payload;
        try {
            payload = objectMapper.writeValueAsBytes(productIds);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar os IDs dos produtos", e);
        }

        Message postMessage = MessageBuilder.withBody(payload)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setCorrelationId(correlationData.getId())
                .setReplyTo(responseQueue)
                .build();
        Message responseMessage = rabbitTemplate.sendAndReceive(EXCHANGE_NAME, REQUEST_ROUTING_KEY, postMessage);

        if (responseMessage == null) {
            throw new RuntimeException("Resposta não recebida");
        }

        return deserializeNotAvailableProductIds(responseMessage.getBody());
    }

    private List<Long> deserializeNotAvailableProductIds(byte[] body) {
        try {
            return objectMapper.readValue(body, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deserializar os IDs dos produtos", e);
        }
    }
}