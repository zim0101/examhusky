package com.app.examhusky.service.rabbitmq.publisher;

import com.app.examhusky.dto.EmailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

    @Value("${examhusky.rabbitmq.exchange.email-exchange}")
    private String emailExchange;

    @Value("${examhusky.rabbitmq.routing-key.user-registration}")
    private String userRegistrationRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserRegistrationEmail(EmailDto emailDto) {
        rabbitTemplate.convertAndSend(emailExchange, userRegistrationRoutingKey, emailDto);
    }
}