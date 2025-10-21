package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.dtos.requests.JobPostingRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")
@CrossOrigin(origins = "*")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @Autowired
    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobPosting>> createJobPosting(@RequestBody JobPostingRequest jobPostingRequest) {
        try {
            JobPosting jobPosting = jobPostingService.createJobPosting(jobPostingRequest);
            ApiResponse<JobPosting> response = new ApiResponse<>(true, "Job posting created successfully", jobPosting);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobPosting>> getJobPostingById(@PathVariable String id) {
        try {
            return jobPostingService.findJobPostingById(id)
                    .map(jobPosting -> {
                        ApiResponse<JobPosting> response = new ApiResponse<>(true, "Job posting retrieved successfully", jobPosting);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<JobPosting> response = new ApiResponse<>(false, "Job posting not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<JobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPosting>>> getAllJobPostings() {
        try {
            List<JobPosting> jobPostings = jobPostingService.findAllJobPostings();
            ApiResponse<List<JobPosting>> response = new ApiResponse<>(true, "Job postings retrieved successfully", jobPostings);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<JobPosting>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobPosting>> updateJobPosting(@PathVariable String id, @RequestBody JobPostingRequest jobPostingRequest) {
        try {
            JobPosting updatedJobPosting = jobPostingService.updateJobPosting(id, jobPostingRequest);
            ApiResponse<JobPosting> response = new ApiResponse<>(true, "Job posting updated successfully", updatedJobPosting);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<JobPosting> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJobPosting(@PathVariable String id) {
        try {
            jobPostingService.deleteJobPosting(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Job posting deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}