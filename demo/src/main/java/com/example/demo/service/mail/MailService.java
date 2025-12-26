package com.example.demo.service.mail;

public interface MailService {
    void sendNewUserPassword(String to, String username, String password);
}
