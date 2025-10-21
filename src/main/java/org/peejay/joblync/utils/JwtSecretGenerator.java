package org.peejay.joblync.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Utility class to generate secure JWT secrets.
 * This class should be used to generate new secrets for production environments.
 */
public class JwtSecretGenerator {

    /**
     * Generates a secure random JWT secret key.
     * @param keyLength the length of the key in bits (typically 256 or 512)
     * @return a base64 encoded string representation of the secret key
     */
    public static String generateSecret(int keyLength) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(keyLength);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT secret", e);
        }
    }

    /**
     * Main method to generate a new JWT secret.
     * Run this method to generate a new secret for your application.
     */
    public static void main(String[] args) {
        String secret = generateSecret(512);
        System.out.println("Generated JWT Secret:");
        System.out.println(secret);
        System.out.println("\nAdd this to your application.properties file:");
        System.out.println("jwt.secret=" + secret);
    }
}