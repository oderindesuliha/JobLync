package org.peejay.joblync.services.impl;

import org.peejay.joblync.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(String to, String firstName, String temporaryPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Welcome to JobLync - Account Registration");
            message.setText(
                    "Dear " + Objects.toString(firstName, "User") + ",\n\n" +
                            "Welcome to JobLync! Your account has been successfully created.\n\n" +
                            "Here are your login credentials:\n" +
                            "Email: " + Objects.toString(to, "N/A") + "\n" +
                            "Password: " + Objects.toString(temporaryPassword, "N/A") + "\n\n" +
                            "Please log in and change your password immediately.\n\n" +
                            "Best regards,\n" +
                            "The JobLync Team"
            );
            mailSender.send(message);
            System.out.println("Registration email sent successfully to: " + to);
        } catch (MailException e) {
            // Log the error but don't fail the operation
            System.err.println("Failed to send registration email to " + to + ": " + e.getMessage());
            System.out.println("In a production environment, this would be logged properly.");
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String firstName, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("JobLync - Password Reset Request");
            message.setText(
                    "Dear " + Objects.toString(firstName, "User") + ",\n\n" +
                            "We received a request to reset your password for your JobLync account.\n\n" +
                            "To reset your password, please click on the following link:\n" +
                            "http://localhost:8080/reset-password?token=" + Objects.toString(resetToken, "") + "\n\n" +
                            "If you did not request a password reset, please ignore this email.\n\n" +
                            "This link will expire in 24 hours.\n\n" +
                            "Best regards,\n" +
                            "The JobLync Team"
            );
            mailSender.send(message);
            System.out.println("Password reset email sent successfully to: " + to);
        } catch (MailException e) {
            // Log the error but don't fail the operation
            System.err.println("Failed to send password reset email to " + to + ": " + e.getMessage());
            System.out.println("In a production environment, this would be logged properly.");
        }
    }

    @Override
    public void sendPasswordEmail(String to, String temporaryPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("JobLync - New Temporary Password");
            message.setText(
                    "Dear User,\n\n" +
                            "Your password has been reset. Here is your new temporary password:\n\n" +
                            "Temporary Password: " + Objects.toString(temporaryPassword, "N/A") + "\n\n" +
                            "Please log in and change your password immediately.\n\n" +
                            "Best regards,\n" +
                            "The JobLync Team"
            );
            mailSender.send(message);
            System.out.println("Password email sent successfully to: " + to);
        } catch (MailException e) {
            // Log the error but don't fail the operation
            System.err.println("Failed to send password email to " + to + ": " + e.getMessage());
            System.out.println("In a production environment, this would be logged properly.");
        }
    }
}