package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.PerformanceReview;
import org.peejay.joblync.data.models.ReviewType;
import org.peejay.joblync.dtos.requests.PerformanceReviewRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance-reviews")
@CrossOrigin(origins = "*")
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    @Autowired
    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PerformanceReview>> createPerformanceReview(@RequestBody PerformanceReviewRequest request) {
        try {
            PerformanceReview performanceReview = performanceReviewService.createPerformanceReview(request);
            ApiResponse<PerformanceReview> response = new ApiResponse<>(true, "Performance review created successfully", performanceReview);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<PerformanceReview> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PerformanceReview>> getPerformanceReviewById(@PathVariable String id) {
        try {
            return performanceReviewService.findPerformanceReviewById(id)
                    .map(performanceReview -> {
                        ApiResponse<PerformanceReview> response = new ApiResponse<>(true, "Performance review retrieved successfully", performanceReview);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<PerformanceReview> response = new ApiResponse<>(false, "Performance review not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<PerformanceReview> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<PerformanceReview>>> getPerformanceReviewsByEmployee(@PathVariable String employeeId) {
        try {
            List<PerformanceReview> performanceReviews = performanceReviewService.findPerformanceReviewsByEmployee(employeeId);
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(true, "Performance reviews retrieved successfully", performanceReviews);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<ApiResponse<List<PerformanceReview>>> getPerformanceReviewsByReviewer(@PathVariable String reviewerId) {
        try {
            List<PerformanceReview> performanceReviews = performanceReviewService.findPerformanceReviewsByReviewer(reviewerId);
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(true, "Performance reviews retrieved successfully", performanceReviews);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{employeeId}/type/{reviewType}")
    public ResponseEntity<ApiResponse<List<PerformanceReview>>> getPerformanceReviewsByEmployeeAndType(
            @PathVariable String employeeId, @PathVariable ReviewType reviewType) {
        try {
            List<PerformanceReview> performanceReviews = performanceReviewService.findPerformanceReviewsByEmployeeAndType(employeeId, reviewType);
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(true, "Performance reviews retrieved successfully", performanceReviews);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<PerformanceReview>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PerformanceReview>> updatePerformanceReview(@PathVariable String id, @RequestBody PerformanceReviewRequest request) {
        try {
            PerformanceReview updatedPerformanceReview = performanceReviewService.updatePerformanceReview(id, request);
            ApiResponse<PerformanceReview> response = new ApiResponse<>(true, "Performance review updated successfully", updatedPerformanceReview);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<PerformanceReview> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePerformanceReview(@PathVariable String id) {
        try {
            performanceReviewService.deletePerformanceReview(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Performance review deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}