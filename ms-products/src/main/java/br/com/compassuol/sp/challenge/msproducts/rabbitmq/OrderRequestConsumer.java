package br.com.compassuol.sp.challenge.msproducts.rabbitmq;

import br.com.compassuol.sp.challenge.msproducts.config.RabbitConfig;
import br.com.compassuol.sp.challenge.msproducts.dtos.OrderRequest;
import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
import br.com.compassuol.sp.challenge.msproducts.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRequestConsumer {

    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;

    public OrderRequestConsumer(ProductService productService, RabbitTemplate rabbitTemplate) {
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public void receiveProductIds(byte[] message) {
        try {
            List<Long> productIds = new ObjectMapper().readValue(message, new TypeReference<List<Long>>() {});

            List<ProductDTO> availableProducts = new ArrayList<>();
            List<Long> notAvailableProductIds = new ArrayList<>();

            for (Long productId : productIds) {
                ProductDTO productDTO = productService.getProductById(productId);
                if (productDTO != null) {
                    availableProducts.add(productDTO);
                } else {
                    notAvailableProductIds.add(productId);
                }
            }

            // Enviar as mensagens apropriadas com base na disponibilidade dos produtos
            if (!availableProducts.isEmpty()) {
                sendProductAvailable(availableProducts);
            }
            if (!notAvailableProductIds.isEmpty()) {
                sendProductNotAvailable(notAvailableProductIds);
            }

        } catch (IOException e) {
            // Handle the deserialization error
            e.printStackTrace();
        }
    }

    private void sendProductAvailable(List<ProductDTO> availableProducts) {
        sendMessage(availableProducts);
    }

    private void sendProductNotAvailable(List<Long> notAvailableProductIds) {
        sendMessage(notAvailableProductIds);
    }

    private void sendMessage(Object content) {
        byte[] body = serialize(content);
        Message responseMessage = MessageBuilder.withBody(body)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.send(RabbitConfig.RESPONSE_QUEUE, responseMessage);
    }

    private byte[] serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}