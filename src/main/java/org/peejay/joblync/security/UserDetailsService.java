package org.peejay.joblync.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByName(String email)
            throws UsernameNotFoundException;
}
