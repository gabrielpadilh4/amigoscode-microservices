package com.github.gabrielpadilh4.customer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static String QUEUE = "customer-created";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

}
