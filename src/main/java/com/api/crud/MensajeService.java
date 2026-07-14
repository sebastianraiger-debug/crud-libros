package com.api.crud;

import com.api.crud.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_LIBROS, mensaje);
        System.out.println("Mensaje enviado a RabbitMQ: " + mensaje);
    }
}