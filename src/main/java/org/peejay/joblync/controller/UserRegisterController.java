package org.peejay.joblync.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.peejay.joblync.dtos.requests.*;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/register")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/applicant")
    public ResponseEntity<ApiResponse> registerApplicant(@RequestBody @Valid ApplicantRegisterRequest request) {
        try {
            UserRegisterResponse response = userService.registerApplicant(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Applicant registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recruiter")
    public ResponseEntity<ApiResponse> registerRecruiter(@RequestBody @Valid RecruiterRegisterRequest request) {
        try {
            UserRegisterResponse response = userService.registerRecruiter(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Recruiter registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse> registerAdmin(@RequestBody @Valid AdminRegisterRequest request) {
        try {
            UserRegisterResponse response = userService.registerAdmin(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Admin registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/hr-manager")
    public ResponseEntity<ApiResponse> registerHRManager(@RequestBody @Valid HRManagerRegisterRequest request) {
        try {
            UserRegisterResponse response = userService.registerHRManager(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "HR Manager registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<ApiResponse> registerEmployee(@RequestBody @Valid EmployeeRegisterRequest request) {
        try {
            UserRegisterResponse response = userService.registerEmployee(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Employee registration failed: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
