package org.peejay.joblync.utils;

import org.peejay.joblync.data.models.*;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.InvalidRoleException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public UserRegisterResponse mapToRegisterResponse(User user) {
        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setActive(user.isActive());
        response.setDateJoined(LocalDateTime.now());
        response.setRole(user.getRole());

        return response;
    }

    public User mapToUser(UserRegisterRequest request) {
        User user;
        switch (request.getRole()) {
            case APPLICANT:
                user = new Applicant();
                break;
            case HR_MANAGER:
                user = new HRManager();
                break;
            case RECRUITER:
                user = new Recruiter();
                break;
            case EMPLOYEE:
                user = new Employee();
                break;
            default:
                throw new InvalidRoleException("Invalid role: " + request.getRole());
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setDateJoined(java.time.LocalDateTime.now());
        user.setActive(true);
        return user;
    }
    }



