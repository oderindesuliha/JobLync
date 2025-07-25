package org.peejay.joblync.services;


public interface JwtService  {
    void blackListToken(String token);
    boolean isBlackListed(String token);
}
