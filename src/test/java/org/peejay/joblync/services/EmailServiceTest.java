package org.peejay.joblync.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTest {

    // Note: This is an integration test that would require a real SMTP server
    // For unit testing, you would typically mock the JavaMailSender
    
    @Test
    public void contextLoads() {
        // This test ensures the Spring context loads properly with the EmailService
    }
}