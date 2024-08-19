package com.greking.Greking.User.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
