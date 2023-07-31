package br.com.compassuol.sp.challenge.msproducts.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;

public class MyResponseHandler {

    private final MessageConverter messageConverter;

    public MyResponseHandler(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
    public void handleResponse(Message responseMessage) {
        // Converte a mensagem AMQP em um objeto de domínio (dependendo do que você está esperando)
        Object response = messageConverter.fromMessage(responseMessage);

        // Aqui você pode processar a resposta como desejar
        // Por exemplo, atualizar um banco de dados, enviar uma notificação, etc.
        System.out.println("Resposta recebida: " + response);
    }
}