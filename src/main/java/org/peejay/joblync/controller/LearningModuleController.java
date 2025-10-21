package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.LearningModule;
import org.peejay.joblync.dtos.requests.LearningModuleRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.LearningModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-modules")
@CrossOrigin(origins = "*")
public class LearningModuleController {

    private final LearningModuleService learningModuleService;

    @Autowired
    public LearningModuleController(LearningModuleService learningModuleService) {
        this.learningModuleService = learningModuleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LearningModule>> createLearningModule(@RequestBody LearningModuleRequest request) {
        try {
            LearningModule learningModule = learningModuleService.createLearningModule(request);
            ApiResponse<LearningModule> response = new ApiResponse<>(true, "Learning module created successfully", learningModule);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<LearningModule> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LearningModule>> getLearningModuleById(@PathVariable String id) {
        try {
            return learningModuleService.findLearningModuleById(id)
                    .map(learningModule -> {
                        ApiResponse<LearningModule> response = new ApiResponse<>(true, "Learning module retrieved successfully", learningModule);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<LearningModule> response = new ApiResponse<>(false, "Learning module not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<LearningModule> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LearningModule>>> getAllLearningModules() {
        try {
            List<LearningModule> learningModules = learningModuleService.findAllLearningModules();
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(true, "Learning modules retrieved successfully", learningModules);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<LearningModule>>> getLearningModulesByCategory(@PathVariable String category) {
        try {
            List<LearningModule> learningModules = learningModuleService.findLearningModulesByCategory(category);
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(true, "Learning modules retrieved successfully", learningModules);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<ApiResponse<List<LearningModule>>> getLearningModulesByProvider(@PathVariable String provider) {
        try {
            List<LearningModule> learningModules = learningModuleService.findLearningModulesByProvider(provider);
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(true, "Learning modules retrieved successfully", learningModules);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<LearningModule>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LearningModule>> updateLearningModule(@PathVariable String id, @RequestBody LearningModuleRequest request) {
        try {
            LearningModule updatedLearningModule = learningModuleService.updateLearningModule(id, request);
            ApiResponse<LearningModule> response = new ApiResponse<>(true, "Learning module updated successfully", updatedLearningModule);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<LearningModule> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLearningModule(@PathVariable String id) {
        try {
            learningModuleService.deleteLearningModule(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Learning module deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}