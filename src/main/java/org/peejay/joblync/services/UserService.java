package org.peejay.joblync.services;

import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.*;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;

import java.util.List;


public interface UserService {

    UserRegisterResponse registerApplicant(ApplicantRegisterRequest request);

    UserRegisterResponse registerRecruiter(RecruiterRegisterRequest recruiterRequest);

    UserRegisterResponse registerAdmin(AdminRegisterRequest adminRequest);

    UserRegisterResponse registerHRManager(HRManagerRegisterRequest hrManagerRequest);

    UserRegisterResponse registerEmployee(EmployeeRegisterRequest request);

    JwtResponse login(UserLoginRequest loginRequest);
    UserRegisterResponse findUserByEmail(String email);
    void logout(String email);
    void updatePassword(String email);
    List<User> getAllUsers();

}
