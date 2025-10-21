package org.peejay.joblync.services;

public interface EmailService {
    void sendRegistrationEmail(String to, String firstName, String temporaryPassword);
    void sendPasswordResetEmail(String to, String firstName, String resetToken);
    void sendPasswordEmail(String to, String temporaryPassword);
}