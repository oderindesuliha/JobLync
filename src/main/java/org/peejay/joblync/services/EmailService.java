package org.peejay.joblync.services;

public interface EmailService {

    void sendRegistrationEmail(String to, String firstName, String lastName);


    void sendPasswordResetEmail(String to, String firstName, String token);

    void sendNotificationEmail(String to, String subject, String body);

    void sendPasswordEmail(String email, String generatedPassword);
}
