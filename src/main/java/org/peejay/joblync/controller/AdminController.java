package org.peejay.joblync.controller;

import lombok.RequiredArgsConstructor;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.UserException;
import org.peejay.joblync.services.AdminService;
import org.peejay.joblync.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Validated
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;


    @PostMapping("/register-sub-admin")
    public ResponseEntity<?> registerSubAdmin(@RequestBody SubAdminRequest request) {

        return ResponseEntity.ok("Welcome to Joblync Admin Panel" + request);
    }


    @PostMapping("/block-user")
    public ResponseEntity<ApiResponse> blockUser(@RequestParam String email) {
        try {
            adminService.disableUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User blocked"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error blocking user: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unblock-user")
    public ResponseEntity<ApiResponse> unblockUser(@RequestParam String email) {
        try {
            adminService.enableUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User unblocked"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error unblocking user: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-user-by-email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String email) {
        try {
            UserRegisterResponse user = userService.findUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse(true, user));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found: " + e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse(true, users));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error fetching users: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam String email) {
        try {
            adminService.deleteUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error deleting user: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

