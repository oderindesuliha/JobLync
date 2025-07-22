package org.peejay.joblync.controller;

import lombok.RequiredArgsConstructor;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.UserException;
import org.peejay.joblync.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserService userService;


    @PostMapping("/register-sub-admin")
    public ResponseEntity<?> registerSubAdmin(@RequestBody SubAdminRequest request) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.registerSubAdmin(request)), HttpStatus.CREATED);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/block-user")
    public ResponseEntity<?> blockUser(@RequestParam String email) {
        try {
            userService.disableUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User blocked"), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unblock-user")
    public ResponseEntity<?> unblockUser(@RequestParam String email) {
        try {
            userService.enableUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User unblocked"), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-user-by-email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            UserRegisterResponse user = userService.findUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse(true, user));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse(true, users));
    }


    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(new ApiResponse(true, "User deleted successfully"), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
