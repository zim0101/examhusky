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

    @Value("${examhusky.rabbitmq.routing-key.user-registration-email}")
    private String userRegistrationRoutingKey;

    @Value("${examhusky.rabbitmq.routing-key.exam-update-email}")
    private String examUpdateEmailRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserRegistrationEmail(EmailDto emailDto) {
        rabbitTemplate.convertAndSend(emailExchange, userRegistrationRoutingKey, emailDto);
    }

    public void sendExamUpdateEmail(EmailDto emailDto) {
        rabbitTemplate.convertAndSend(emailExchange, examUpdateEmailRoutingKey, emailDto);
    }
}