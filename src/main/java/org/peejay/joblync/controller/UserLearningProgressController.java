package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.CompletionStatus;
import org.peejay.joblync.data.models.UserLearningProgress;
import org.peejay.joblync.dtos.requests.UserLearningProgressRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.UserLearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-learning-progress")
@CrossOrigin(origins = "*")
public class UserLearningProgressController {

    private final UserLearningProgressService userLearningProgressService;

    @Autowired
    public UserLearningProgressController(UserLearningProgressService userLearningProgressService) {
        this.userLearningProgressService = userLearningProgressService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserLearningProgress>> trackUserLearningProgress(@RequestBody UserLearningProgressRequest request) {
        try {
            UserLearningProgress userLearningProgress = userLearningProgressService.trackUserLearningProgress(request);
            ApiResponse<UserLearningProgress> response = new ApiResponse<>(true, "User learning progress tracked successfully", userLearningProgress);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<UserLearningProgress> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserLearningProgress>> getUserLearningProgressById(@PathVariable String id) {
        try {
            return userLearningProgressService.findUserLearningProgressById(id)
                    .map(userLearningProgress -> {
                        ApiResponse<UserLearningProgress> response = new ApiResponse<>(true, "User learning progress retrieved successfully", userLearningProgress);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<UserLearningProgress> response = new ApiResponse<>(false, "User learning progress not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<UserLearningProgress> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserLearningProgress>>> getUserLearningProgressByUser(@PathVariable String userId) {
        try {
            List<UserLearningProgress> userLearningProgressList = userLearningProgressService.findUserLearningProgressByUser(userId);
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(true, "User learning progress retrieved successfully", userLearningProgressList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<ApiResponse<List<UserLearningProgress>>> getUserLearningProgressByModule(@PathVariable String moduleId) {
        try {
            List<UserLearningProgress> userLearningProgressList = userLearningProgressService.findUserLearningProgressByModule(moduleId);
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(true, "User learning progress retrieved successfully", userLearningProgressList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<ApiResponse<List<UserLearningProgress>>> getUserLearningProgressByUserAndStatus(
            @PathVariable String userId, @PathVariable CompletionStatus status) {
        try {
            List<UserLearningProgress> userLearningProgressList = userLearningProgressService.findUserLearningProgressByUserAndStatus(userId, status);
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(true, "User learning progress retrieved successfully", userLearningProgressList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserLearningProgress>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserLearningProgress>> updateUserLearningProgress(@PathVariable String id, @RequestBody UserLearningProgressRequest request) {
        try {
            UserLearningProgress updatedUserLearningProgress = userLearningProgressService.updateUserLearningProgress(id, request);
            ApiResponse<UserLearningProgress> response = new ApiResponse<>(true, "User learning progress updated successfully", updatedUserLearningProgress);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<UserLearningProgress> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeUserLearningProgress(@PathVariable String id) {
        try {
            userLearningProgressService.removeUserLearningProgress(id);
            ApiResponse<String> response = new ApiResponse<>(true, "User learning progress removed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}