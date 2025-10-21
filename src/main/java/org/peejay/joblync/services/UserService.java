package org.peejay.joblync.services;

import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    JwtResponse loginUser(UserLoginRequest userLoginRequest);
    Optional<User> findByEmail(String email);
    List<User> findAllUsers();
    User updateUser(String id, User user);
    void deleteUser(String id);
    boolean existsByEmail(String email);
    void resetUserPassword(String email);
    
    // Additional methods
    Optional<User> findById(String id);
    List<User> findByDepartment(String department);
    List<User> findByRole(org.peejay.joblync.data.models.Role role);
    List<User> findSubordinates(String managerId);
    User changeUserRole(String userId, org.peejay.joblync.data.models.Role newRole);
    User updateEmployeeInfo(String userId, User employeeInfo);
}