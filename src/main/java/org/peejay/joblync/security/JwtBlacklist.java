package org.peejay.joblync.security;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtBlacklist {

    private Set<String> blacklist = Collections.synchronizedSet(new HashSet<>());

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public void removeFromBlacklist(String token) {
        blacklist.remove(token);
    }
}

