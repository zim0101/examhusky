package com.app.examhusky.service.rabbitmq.listener;

import com.app.examhusky.dto.EmailDto;
import com.app.examhusky.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationEmailListener {

    private final EmailService emailService;

    public UserRegistrationEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${examhusky.rabbitmq.queue.user-registration-email}")
    public void processUserRegistrationEmail(EmailDto emailDto) {
        emailService.sendMail(emailDto);
    }
}