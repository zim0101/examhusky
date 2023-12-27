package com.app.examhusky.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    // RabbitMQ server connection credentials
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    // RabbitMQ Email exchange for all email queues
    @Value("${examhusky.rabbitmq.exchange.email-exchange}")
    private String emailExchange;

    // User registration queue properties
    @Value("${examhusky.rabbitmq.queue.user-registration-email}")
    private String userRegistrationEmailQueue;

    @Value("${examhusky.rabbitmq.routing-key.user-registration-email}")
    private String userRegistrationEmailRoutingKey;

    // Exam update queue properties

    @Value("${examhusky.rabbitmq.queue.exam-update-email}")
    private String examUpdateEmailQueue;

    @Value("${examhusky.rabbitmq.routing-key.exam-update-email}")
    private String examUpdateEmailRoutingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(emailExchange);
    }

    @Bean
    public Queue userRegistrationEmailQueue() {
        return new Queue(userRegistrationEmailQueue, true);
    }

    @Bean
    public Binding bindingUserRegistrationEmailQueue(Queue userRegistrationEmailQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(userRegistrationEmailQueue)
                .to(emailExchange)
                .with(userRegistrationEmailRoutingKey);
    }

    @Bean
    public Queue examUpdateEmailQueue() {
        return new Queue(examUpdateEmailQueue, true);
    }

    @Bean
    public Binding bindingExamUpdateEmailQueue(Queue examUpdateEmailQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(examUpdateEmailQueue)
                .to(emailExchange)
                .with(examUpdateEmailRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}