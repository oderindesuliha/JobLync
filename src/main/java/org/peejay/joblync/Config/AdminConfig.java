package org.peejay.joblync.Config;

import lombok.RequiredArgsConstructor;
import org.peejay.joblync.data.models.Admin;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class AdminConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppAdminProperties adminProps;

    @Override
    public void run(String... args) {
        String adminEmail = adminProps.getAdmin().getEmail();
        String subAdminEmail = adminProps.getSubadmin().getEmail();

        if (!userRepository.existsByEmail(adminEmail)) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminProps.getAdmin().getPassword()));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setActive(true);
            admin.setAdminLevel(adminProps.getAdmin().getLevel());
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail(subAdminEmail)) {
            Admin subAdmin = new Admin();
            subAdmin.setEmail(subAdminEmail);
            subAdmin.setPassword(passwordEncoder.encode(adminProps.getSubadmin().getPassword()));
            subAdmin.setRole(Role.SUB_ADMIN);
            subAdmin.setFirstName("Sub");
            subAdmin.setLastName("Admin");
            subAdmin.setActive(true);
            subAdmin.setAdminLevel(adminProps.getSubadmin().getLevel());
            userRepository.save(subAdmin);
        }
    }
}