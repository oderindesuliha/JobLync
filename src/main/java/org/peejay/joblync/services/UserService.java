package org.peejay.joblync.services;

import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.requests.subAdminRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;

import java.util.List;


public interface UserService {
    UserRegisterResponse registerUser(UserRegisterRequest request);
    JwtResponse login(UserLoginRequest loginRequest);
    UserRegisterRequest getUserByEmail(String email);
    void logout(String email);
    void updatePassword(String email, String password);
    void createSubAdmin(subAdminRequest request);
    void disableUser(String email);
    void enableUser(String email);
    void deleteUser(String email);
    List<User> getAllUsers();

}
