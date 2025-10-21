package org.peejay.joblync.controller;

import org.peejay.joblync.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/test-registration")
    public ResponseEntity<String> sendTestRegistrationEmail(@RequestParam String to, 
                                                           @RequestParam String firstName) {
        try {
            emailService.sendRegistrationEmail(to, firstName, "TempPass123");
            return ResponseEntity.ok("Registration email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + Objects.toString(e.getMessage(), "Unknown error"));
        }
    }

    @PostMapping("/test-password-reset")
    public ResponseEntity<String> sendTestPasswordResetEmail(@RequestParam String to, 
                                                            @RequestParam String firstName) {
        try {
            emailService.sendPasswordResetEmail(to, firstName, "resetToken123");
            return ResponseEntity.ok("Password reset email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + Objects.toString(e.getMessage(), "Unknown error"));
        }
    }

    @PostMapping("/test-password")
    public ResponseEntity<String> sendTestPasswordEmail(@RequestParam String to) {
        try {
            emailService.sendPasswordEmail(to, "NewPass123");
            return ResponseEntity.ok("Password email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + Objects.toString(e.getMessage(), "Unknown error"));
        }
    }
}