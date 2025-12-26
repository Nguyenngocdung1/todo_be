package com.example.demo.service.mail.impl;

import com.example.demo.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendNewUserPassword(String to, String username, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your account information");
        message.setText("""
                Hello %s,

                Your account has been created.

                Username: %s
                Password: %s

                Please login and change your password.
                """.formatted(username, username, password));

        mailSender.send(message);
    }
}
