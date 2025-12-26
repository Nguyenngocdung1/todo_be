package com.example.demo.service.password;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
    private static final int DEFAULT_LENGTH = 10;

    public static String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(DEFAULT_LENGTH);

        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
