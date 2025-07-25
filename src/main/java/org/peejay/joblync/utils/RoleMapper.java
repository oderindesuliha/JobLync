package org.peejay.joblync.utils;

import org.peejay.joblync.data.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RoleMapper {

    public static User mapToRole(User user) {
        User mappedUser;

        switch (user.getRole()) {
            case APPLICANT -> {
                Applicant applicant = new Applicant();
                applicant.setResumeUrl(null);
                applicant.setPortfolioUrl(null);
                applicant.setStatus(ApplicationStatus.PENDING);
                mappedUser = applicant;
            }
            case EMPLOYEE -> {
                Employee employee = new Employee();
                employee.setJobTitle(null);
                employee.setCompanyName(null);
                employee.setStartDate(LocalDateTime.now());
                employee.setHrManager(null);
                mappedUser = employee;
            }
            case HR_MANAGER -> {
                HRManager manager = new HRManager();
                mappedUser = manager;
            }
            case RECRUITER -> {
                Recruiter recruiter = new Recruiter();
                mappedUser = recruiter;
            }
            default -> throw new IllegalArgumentException("Unknown role: " + user.getRole());
        }

        commonFields(user, mappedUser);
        return mappedUser;
    }

    private static void commonFields(User source, User target) {
        target.setId(source.getId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setPassword(source.getPassword());
        target.setProfilePicture(source.getProfilePicture());
        target.setDateJoined(source.getDateJoined());
        target.setActive(source.isActive());
        target.setRole(source.getRole());
    }
}
