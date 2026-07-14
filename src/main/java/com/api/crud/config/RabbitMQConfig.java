package com.api.crud.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String COLA_LIBROS = "cola-libros";

    @Bean
    public Queue colaLibros() {
        return new Queue(COLA_LIBROS, true);
    }
}
