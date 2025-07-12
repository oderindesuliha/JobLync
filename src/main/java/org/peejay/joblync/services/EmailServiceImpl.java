package org.peejay.joblync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendRegistrationEmail(String to, String firstName, String lastName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Joblync!");
        message.setText("Dear " + firstName + " " + lastName + ",\n\nThank you for registering with Joblync. Your account is now active.\n\nBest,\nJoblync Team");
        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetEmail(String to, String firstName, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Dear " + firstName + ",\n\nTo reset your password, click this link: https://joblync.com/reset?token=" + token + "\n\nBest,\nJoblync Team");
        mailSender.send(message);
    }

    @Override
    public void sendNotificationEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}