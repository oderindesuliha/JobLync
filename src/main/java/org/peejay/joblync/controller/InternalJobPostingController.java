package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.InternalJobPosting;
import org.peejay.joblync.dtos.requests.InternalJobPostingRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.InternalJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internal-job-postings")
@CrossOrigin(origins = "*")
public class InternalJobPostingController {

    private final InternalJobPostingService internalJobPostingService;

    @Autowired
    public InternalJobPostingController(InternalJobPostingService internalJobPostingService) {
        this.internalJobPostingService = internalJobPostingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InternalJobPosting>> createInternalJobPosting(@RequestBody InternalJobPostingRequest request) {
        try {
            // In a real implementation, you would get the creator ID from the authenticated user
            InternalJobPosting internalJobPosting = internalJobPostingService.createInternalJobPosting(request, "HR_MANAGER_ID");
            ApiResponse<InternalJobPosting> response = new ApiResponse<>(true, "Internal job posting created successfully", internalJobPosting);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<InternalJobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InternalJobPosting>> getInternalJobPostingById(@PathVariable String id) {
        try {
            return internalJobPostingService.findInternalJobPostingById(id)
                    .map(internalJobPosting -> {
                        ApiResponse<InternalJobPosting> response = new ApiResponse<>(true, "Internal job posting retrieved successfully", internalJobPosting);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<InternalJobPosting> response = new ApiResponse<>(false, "Internal job posting not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<InternalJobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InternalJobPosting>>> getAllInternalJobPostings() {
        try {
            List<InternalJobPosting> internalJobPostings = internalJobPostingService.findAllInternalJobPostings();
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(true, "Internal job postings retrieved successfully", internalJobPostings);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/open")
    public ResponseEntity<ApiResponse<List<InternalJobPosting>>> getOpenInternalJobPostings() {
        try {
            List<InternalJobPosting> internalJobPostings = internalJobPostingService.findOpenInternalJobPostings();
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(true, "Open internal job postings retrieved successfully", internalJobPostings);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<InternalJobPosting>>> getInternalJobPostingsByDepartment(@PathVariable String department) {
        try {
            List<InternalJobPosting> internalJobPostings = internalJobPostingService.findInternalJobPostingsByDepartment(department);
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(true, "Internal job postings retrieved successfully", internalJobPostings);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/level/{experienceLevel}")
    public ResponseEntity<ApiResponse<List<InternalJobPosting>>> getInternalJobPostingsByExperienceLevel(@PathVariable ExperienceLevel experienceLevel) {
        try {
            List<InternalJobPosting> internalJobPostings = internalJobPostingService.findInternalJobPostingsByExperienceLevel(experienceLevel);
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(true, "Internal job postings retrieved successfully", internalJobPostings);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<InternalJobPosting>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InternalJobPosting>> updateInternalJobPosting(@PathVariable String id, @RequestBody InternalJobPostingRequest request) {
        try {
            InternalJobPosting updatedInternalJobPosting = internalJobPostingService.updateInternalJobPosting(id, request);
            ApiResponse<InternalJobPosting> response = new ApiResponse<>(true, "Internal job posting updated successfully", updatedInternalJobPosting);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<InternalJobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInternalJobPosting(@PathVariable String id) {
        try {
            internalJobPostingService.deleteInternalJobPosting(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Internal job posting deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse<String>> closeInternalJobPosting(@PathVariable String id) {
        try {
            internalJobPostingService.closeInternalJobPosting(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Internal job posting closed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}