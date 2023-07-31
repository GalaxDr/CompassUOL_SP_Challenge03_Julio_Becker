//package br.com.compassuol.sp.challenge.msproducts.rabbitmq;
//
//import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
//import br.com.compassuol.sp.challenge.msproducts.service.ProductService;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ProductNotFoundProducer {
//    private final RabbitTemplate rabbitTemplate;
//
//    public ProductNotFoundProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendProductNotAvailable(List<Long> productIds) {
//        System.out.println("Sending second message to ms-orders with not available products");
//        rabbitTemplate.convertAndSend("Product-Not-Found-Exchange", "product.not.available", productIds);
//    }
//
//    public void sendProductsToMsOrders(List<ProductDTO> products) {
//        System.out.println("Sending products to ms-orders");
//        rabbitTemplate.convertAndSend("Product-Available-Exchange", "product.available", products);
//    }
//    @RabbitListener(queues = "Product-Not-Available-Queue")
//    public void receiveProductNotAvailable(List<Long> productIds) {
//        System.out.println("Received second message from ms-products");
//    }
//}