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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists");
        }
        User user = userMapper.mapToUser(request);
        User savedUser = userRepository.save(user);

    return userMapper.mapToRegisterResponse(savedUser);
    }

    @Override
    public JwtResponse login(UserLoginRequest request) {

        Optional<User> userOptional =  userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new UserException("Invalid email or password");
        }

        User user = userOptional.get();

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
