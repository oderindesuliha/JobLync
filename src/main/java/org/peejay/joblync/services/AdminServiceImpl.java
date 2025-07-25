package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.UserException;
import org.peejay.joblync.security.JwtUtil;
import org.peejay.joblync.utils.UserMapper;
import org.peejay.joblync.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserValidations userValidation;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";



    @Override
    @Transactional
    public void disableUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        User existingUser = user.get();
        if (!existingUser.isActive()) {
            throw new UserException("User is already disabled");
        }
        existingUser.setActive(false);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void enableUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        User existingUser = user.get();
        if (existingUser.isActive()) {
            throw new UserException("User is already enabled");
        }
        existingUser.setActive(true);
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        userRepository.delete(user.get());
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int count = 0; count < 8; count++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}


