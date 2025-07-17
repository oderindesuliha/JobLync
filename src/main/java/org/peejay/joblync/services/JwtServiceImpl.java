package org.peejay.joblync.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtServiceImpl implements JwtService{
    private final Set<String> blacklist = new HashSet<>();

    @Override
    public void blackListToken(String token) {

        blacklist.add(token);
    }

    @Override
    public boolean isBlackListed(String token) {

        return blacklist.contains(token);
    }
}
