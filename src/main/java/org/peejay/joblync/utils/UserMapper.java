package org.peejay.joblync.utils;

import org.peejay.joblync.data.models.*;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.InvalidRoleException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper {

    public User mapToUser(UserRegisterRequest request) {
        User user;

        switch (request.getRole()) {
            case APPLICANT -> {
                Applicant applicant = new Applicant();
                applicant.setResumeUrl(null);
                applicant.setPortfolioUrl(null);
                applicant.setStatus(ApplicationStatus.PENDING);
                user = applicant;
            }
            case HR_MANAGER -> {
                HRManager hrManager = new HRManager();
                hrManager.setTeamName(null);
                hrManager.setSenior(false);
                hrManager.setEmployees(List.of());
                user = hrManager;
            }
            case RECRUITER -> {
                Recruiter recruiter = new Recruiter();
                recruiter.setNumberOfHires(0);
                user = recruiter;
            }
            case EMPLOYEE -> {
                Employee employee = new Employee();
                employee.setJobTitle(null);
                employee.setCompanyName(null);
                employee.setStartDate(LocalDateTime.now());
                employee.setHrManager(null);
                user = employee;
            }
            case ADMIN, SUB_ADMIN -> user = new User();
            default -> throw new InvalidRoleException("Invalid role: " + request.getRole());
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDateJoined(LocalDateTime.now());
        user.setActive(true);

        return user;
    }

    public User mapToUser(SubAdminRequest request) {
        User user = new User();
        String[] nameParts = request.getFullName().trim().split("\\s+", 2);
        user.setFirstName(nameParts[0]);
        user.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        user.setEmail(request.getEmail());
        user.setPassword(null);
        user.setRole(Role.SUB_ADMIN);
        user.setDateJoined(LocalDateTime.now());
        user.setActive(true);
        return user;
    }

    public UserRegisterResponse mapToRegisterResponse(User user) {
        UserRegisterResponse response = new UserRegisterResponse();
        response.setUserId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setDateJoined(user.getDateJoined());
        response.setActive(user.isActive());
        return response;
    }
}
