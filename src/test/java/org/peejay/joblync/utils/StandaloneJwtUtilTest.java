package org.peejay.joblync.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Standalone test for JWT functionality without requiring Spring context or database.
 */
public class StandaloneJwtUtilTest {

    private static final String SECRET = "5B0970DFF635D80A4E9B2D9E8F4A7C3B1F2E4D7A9C0B3E6F8A1D5C9E2F7A4B8C";
    private static final Long EXPIRATION = 86400000L; // 24 hours
    private static final String ALGORITHM = "HS512";
    private static final String ISSUER = "JobLync";
    private static final String AUDIENCE = "JobLyncUsers";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .signWith(getSigningKey(), SignatureAlgorithm.valueOf(ALGORITHM))
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Test
    public void testGenerateAndValidateToken() {
        // Generate a token
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        String email = "test@example.com";
        String token = createToken(claims, email);

        // Validate that token is not null or empty
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Extract claims
        Claims extractedClaims = extractAllClaims(token);
        
        // Validate claims
        assertEquals(email, extractedClaims.getSubject());
        assertEquals("ADMIN", extractedClaims.get("role"));
        assertEquals(ISSUER, extractedClaims.getIssuer());
        assertEquals(AUDIENCE, extractedClaims.getAudience());
        
        // Validate token is not expired
        assertFalse(isTokenExpired(token));
    }

    @Test
    public void testTokenExpiration() throws InterruptedException {
        // Create a token with very short expiration time
        String secret = "5B0970DFF635D80A4E9B2D9E8F4A7C3B1F2E4D7A9C0B3E6F8A1D5C9E2F7A4B8C";
        Long shortExpiration = 100L; // 100 milliseconds
        String algorithm = "HS512";
        String issuer = "JobLync";
        String audience = "JobLyncUsers";

        SecretKey signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + shortExpiration);

        String token = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signingKey, SignatureAlgorithm.valueOf(algorithm))
                .compact();

        Thread.sleep(150);

        assertThrows(ExpiredJwtException.class, () -> {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
        });
    }
}