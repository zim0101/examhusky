package com.app.examhusky.service;

import com.app.examhusky.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${examhusky.email.default.display-name}")
    private String defaultDisplayName;

    @Value("${examhusky.email.default.sender-address}")
    private String defaultSenderAddress;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(EmailDto email) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(email.getMailSubject());
            mailMessage.setFrom(String.valueOf(new InternetAddress(
                    email.getMailFrom() != null ? email.getMailFrom() : defaultSenderAddress,
                    email.getDisplayName() != null ? email.getDisplayName() : defaultDisplayName)));
            mailMessage.setTo(email.getMailTo());
            mailMessage.setText(email.getMailContent());
            mailSender.send(mailMessage);
        } catch (MailException | UnsupportedEncodingException e) {
            log.error(e.getLocalizedMessage());
        }
    }


    public void sendEmailMime(EmailDto email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject(email.getMailSubject());
        mimeMessageHelper.setFrom(new InternetAddress(email.getMailFrom(), email.getDisplayName()));
        mimeMessageHelper.setTo(email.getMailTo());
        mimeMessageHelper.setText(email.getMailContent());

        mailSender.send(mimeMessageHelper.getMimeMessage());
    }
}