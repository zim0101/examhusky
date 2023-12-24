package com.app.examhusky.service.rabbitmq.listener;

import com.app.examhusky.dto.EmailDto;
import com.app.examhusky.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ExamUpdateEmailListener {
    private final EmailService emailService;

    public ExamUpdateEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${examhusky.rabbitmq.queue.exam-update-email}")
    public void processExamUpdateEmail(EmailDto emailDto) {
        emailService.sendMail(emailDto);
    }
}