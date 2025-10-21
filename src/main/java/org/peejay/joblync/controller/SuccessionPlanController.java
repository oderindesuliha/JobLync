package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.SuccessionPlan;
import org.peejay.joblync.data.models.SuccessionStatus;
import org.peejay.joblync.dtos.requests.SuccessionPlanRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.SuccessionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/succession-plans")
@CrossOrigin(origins = "*")
public class SuccessionPlanController {

    private final SuccessionPlanService successionPlanService;

    @Autowired
    public SuccessionPlanController(SuccessionPlanService successionPlanService) {
        this.successionPlanService = successionPlanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SuccessionPlan>> createSuccessionPlan(@RequestBody SuccessionPlanRequest request) {
        try {
            SuccessionPlan successionPlan = successionPlanService.createSuccessionPlan(request);
            ApiResponse<SuccessionPlan> response = new ApiResponse<>(true, "Succession plan created successfully", successionPlan);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<SuccessionPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SuccessionPlan>> getSuccessionPlanById(@PathVariable String id) {
        try {
            return successionPlanService.findSuccessionPlanById(id)
                    .map(successionPlan -> {
                        ApiResponse<SuccessionPlan> response = new ApiResponse<>(true, "Succession plan retrieved successfully", successionPlan);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<SuccessionPlan> response = new ApiResponse<>(false, "Succession plan not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<SuccessionPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/incumbent/{incumbentId}")
    public ResponseEntity<ApiResponse<List<SuccessionPlan>>> getSuccessionPlansByIncumbent(@PathVariable String incumbentId) {
        try {
            List<SuccessionPlan> successionPlans = successionPlanService.findSuccessionPlansByIncumbent(incumbentId);
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(true, "Succession plans retrieved successfully", successionPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/successor/{successorId}")
    public ResponseEntity<ApiResponse<List<SuccessionPlan>>> getSuccessionPlansBySuccessor(@PathVariable String successorId) {
        try {
            List<SuccessionPlan> successionPlans = successionPlanService.findSuccessionPlansBySuccessor(successorId);
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(true, "Succession plans retrieved successfully", successionPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<SuccessionPlan>>> getSuccessionPlansByDepartment(@PathVariable String department) {
        try {
            List<SuccessionPlan> successionPlans = successionPlanService.findSuccessionPlansByDepartment(department);
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(true, "Succession plans retrieved successfully", successionPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<SuccessionPlan>>> getSuccessionPlansByStatus(@PathVariable SuccessionStatus status) {
        try {
            List<SuccessionPlan> successionPlans = successionPlanService.findSuccessionPlansByStatus(status);
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(true, "Succession plans retrieved successfully", successionPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/readiness-level/{minReadinessLevel}")
    public ResponseEntity<ApiResponse<List<SuccessionPlan>>> getSuccessionPlansByReadinessLevel(@PathVariable Integer minReadinessLevel) {
        try {
            List<SuccessionPlan> successionPlans = successionPlanService.findSuccessionPlansByReadinessLevel(minReadinessLevel);
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(true, "Succession plans retrieved successfully", successionPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<SuccessionPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SuccessionPlan>> updateSuccessionPlan(@PathVariable String id, @RequestBody SuccessionPlanRequest request) {
        try {
            SuccessionPlan updatedSuccessionPlan = successionPlanService.updateSuccessionPlan(id, request);
            ApiResponse<SuccessionPlan> response = new ApiResponse<>(true, "Succession plan updated successfully", updatedSuccessionPlan);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<SuccessionPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSuccessionPlan(@PathVariable String id) {
        try {
            successionPlanService.deleteSuccessionPlan(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Succession plan deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/fill")
    public ResponseEntity<ApiResponse<String>> markSuccessionPlanAsFilled(@PathVariable String id) {
        try {
            successionPlanService.markAsFilled(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Succession plan marked as filled successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}