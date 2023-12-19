package com.app.examhusky.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${examhusky.rabbitmq.exchange.email-exchange}")
    private String emailExchange;

    @Value("${examhusky.rabbitmq.queue.user-registration}")
    private String userRegistrationQueue;

    @Value("${examhusky.rabbitmq.routing-key.user-registration}")
    private String userRegistrationRoutingKey;

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
        return new Queue(userRegistrationQueue, true);
    }

    @Bean
    public Binding bindingRegistration(Queue userRegistrationEmailQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(userRegistrationEmailQueue)
                .to(emailExchange)
                .with(userRegistrationRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}