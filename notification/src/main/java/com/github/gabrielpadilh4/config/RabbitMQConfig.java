package com.github.gabrielpadilh4.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "customer-created";

    @Bean Queue queue(){
        return new Queue(QUEUE, true);
    }

}
