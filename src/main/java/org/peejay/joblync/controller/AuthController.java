package org.peejay.joblync.controller;

import jakarta.validation.Valid;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.ErrorResponse;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            UserRegisterResponse response = userService.registerUser(userRegisterRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse("Registration failed", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Something went wrong", "Please try again later");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        try {
            JwtResponse response = userService.loginUser(userLoginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse("Login failed", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("Something went wrong", "Please try again later");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}