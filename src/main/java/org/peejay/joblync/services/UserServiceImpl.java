package org.peejay.joblync.services;

import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.requests.subAdminRequest;
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
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{

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
        Optional<User> savedUser =  userRepository.findByEmail(request.getEmail());
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
        return null;
    }

    @Override
    public void logout(String email) {

    }

    @Override
    public void updatePassword(String email, String password) {

    }

    @Override
    public void registerSubAdmin(subAdminRequest request) {

    }

    @Override
    public void disableUser(String email) {

    }

    @Override
    public void enableUser(String email) {

    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
