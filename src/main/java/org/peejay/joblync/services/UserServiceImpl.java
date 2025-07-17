package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.UserException;
import org.peejay.joblync.utils.UserMapper;
import org.peejay.joblync.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    @Transactional
    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        userValidation.validateUserRegisterRequest(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists");
        }
        User user = userMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        emailService.sendRegistrationEmail(user.getEmail(), user.getFirstName(), user.getLastName());
        return userMapper.mapToRegisterResponse(savedUser);
    }

    @Override
    public JwtResponse login(UserLoginRequest request) {
        userValidation.validateUserLoginRequest(request);
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isEmpty()) {
            throw new UserException("Invalid email or password");
        }
        User user = savedUser.get();
        if (!user.isActive()) {
            throw new UserException("User is not active");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password");
        }
        return new JwtResponse();
    }

    @Override
    public UserRegisterResponse findUserByEmail(String email) {
        userValidation.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        return userMapper.mapToRegisterResponse(user.get());
    }

    @Override
    @Transactional
    public void updatePassword(String email, String password) {
        userValidation.validateEmail(email);
        userValidation.validatePassword(password);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        User existingUser = user.get();
        existingUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(existingUser);
        emailService.sendPasswordResetEmail(email, existingUser.getFirstName(), existingUser.getLastName());
    }

    @Override
    @Transactional
    public UserRegisterResponse registerSubAdmin(SubAdminRequest request) {
        userValidation.validateSubAdminRequest(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists");
        }
        User user = userMapper.mapToUser(request);
        String generatedPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(generatedPassword));
        user.setRole(Role.SUB_ADMIN);
        User savedUser = userRepository.save(user);
        emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getFirstName(), savedUser.getLastName());
        emailService.sendPasswordEmail(savedUser.getEmail(), generatedPassword);
        return userMapper.mapToRegisterResponse(savedUser);
    }

    @Override
    @Transactional
    public void disableUser(String email) {
        userValidation.validateEmail(email);
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
        userValidation.validateEmail(email);
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
        userValidation.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void logout(String email) {
        userValidation.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
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