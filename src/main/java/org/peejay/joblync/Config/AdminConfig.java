package org.peejay.joblync.Config;

import lombok.RequiredArgsConstructor;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.security.JwtUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("oderindesuliha@joblync.com")){

            User admin = new User();
            admin.setEmail("oderindesuliha@joblync.com");
            admin.setPassword(passwordEncoder.encode("admin1"));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setActive(true);

            userRepository.save(admin);
        }
        if (!userRepository.existsByEmail("peejay@joblync.com")) {

            User subAdmin = new User();
            subAdmin.setEmail("peejay@sme.com");
            subAdmin.setPassword(passwordEncoder.encode("admin2"));
            subAdmin.setRole(Role.ADMIN);
            subAdmin.setFirstName("Sub");
            subAdmin.setLastName("Admin");
            subAdmin.setActive(true);

            userRepository.save(subAdmin);
        }


    }


}
