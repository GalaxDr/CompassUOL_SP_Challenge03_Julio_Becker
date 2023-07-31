//package br.com.compassuol.sp.challenge.msorders.rabbitmq;
//
//import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class ProductsConsumer {
//    @RabbitListener(queues = "Product-Not-Found")
//    public void receiveProductNotAvailable(Long productId) {
//        System.out.println("Product with ID " + productId + " is not available.");
//    }
//    @RabbitListener(queues = "Products-Response-Queue")
//    public Message receiveProductsFromMsProducts() {
//    System.out.println("Received products from ms-products");
//        return null;
//    }
//}