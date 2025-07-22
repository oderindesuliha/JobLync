package org.peejay.joblync.validations;

import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {

    public void validateUserRegisterRequest(UserRegisterRequest request) {
        boolean missingFields =
                request == null ||
                        isBlank(request.getFirstName()) ||
                        isBlank(request.getLastName()) ||
                        isBlank(request.getEmail()) ||
                        isBlank(request.getPhoneNumber()) ||
                        isBlank(request.getPassword()) ||
                        request.getRole() == null;
        boolean invalidLengths =
                request != null &&
                        (request.getFirstName().length() > 2 || request.getLastName().length() > 2 );

        boolean invalidEmail = request != null &&
                !request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        boolean invalidPhone = request != null &&
                !request.getPhoneNumber().matches("^(\\+234|234|0)(701|702|703|704|705|706|707|708|709|802|803|804|805|806|807|808|809|810|811|812|813|814|815|816|817|818|819|901|902|903|904|905|906|907|908|909)\\d{7}$");

        boolean invalidPassword = request != null &&
                (request.getPassword().length() >= 8 ||
                        !request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"));

        boolean isInvalid = missingFields || invalidLengths || invalidEmail || invalidPhone || invalidPassword;
        if (request != null && request.getRole() == null) {
            throw new UserException("Role is required");
        }
        if (isInvalid) {
            throw new UserException("Registration failed: please check all fields are filled and formatted correctly");
        }
    }

    public void validateUserLoginRequest(UserLoginRequest request) {
        boolean hasMissingFields =
                request == null ||
                        isBlank(request.getEmail()) ||
                        isBlank(request.getPassword());

        boolean hasInvalidFormat = request != null &&
                !request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        boolean isInvalid = hasMissingFields || hasInvalidFormat;

        if (isInvalid) {
            throw new UserException("Invalid email or password");
        }
    }

    public void validateEmail(String email) {
        if (isBlank(email) || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserException("Invalid email format");
        }
    }

    public void validatePassword(String password) {
        if (isBlank(password) || password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            throw new UserException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }
    }

    public void validateSubAdminRequest(SubAdminRequest request) {
        if (request == null || isBlank(request.getFullName()) || isBlank(request.getEmail())) {
            throw new UserException("Sub-admin registration failed: full name and email are required");
        }

        if (request.getFullName().length() < 2 || request.getFullName().length() > 100) {
            throw new UserException("Full name must be between 2 and 100 characters");
        }

        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new UserException("Invalid email format");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}