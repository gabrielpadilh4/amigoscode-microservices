package com.github.gabrielpadilh4.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gabrielpadilh4.config.RabbitMQConfig;
import com.github.gabrielpadilh4.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumer(@Payload CustomerDTO customerDTO) {

        log.info("new customer to send notification {}", customerDTO);

        // todo: send the notification
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
