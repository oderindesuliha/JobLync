package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @Autowired
public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobApplication>> applyForJob(@RequestBody JobApplicationRequest jobApplicationRequest) {
        try {
            // Check if user has already applied for this job
            if (jobApplicationService.hasUserAppliedForJob(jobApplicationRequest.getApplicantId(), jobApplicationRequest.getJobPostingId())) {
                ApiResponse<JobApplication> response = new ApiResponse<>(false, "User has already applied for this job", null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            JobApplication jobApplication = jobApplicationService.applyForJob(jobApplicationRequest);
            ApiResponse<JobApplication> response = new ApiResponse<>(true, "Job application submitted successfully", jobApplication);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<JobApplication> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplication>> getJobApplicationById(@PathVariable String id) {
        try {
            return jobApplicationService.findJobApplicationById(id)
                    .map(jobApplication -> {
                        ApiResponse<JobApplication> response = new ApiResponse<>(true, "Job application retrieved successfully", jobApplication);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<JobApplication> response = new ApiResponse<>(false, "Job application not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<JobApplication> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<ApiResponse<List<JobApplication>>> getJobApplicationsByApplicant(@PathVariable String applicantId) {
        try {
            List<JobApplication> jobApplications = jobApplicationService.findJobApplicationsByApplicant(applicantId);
            ApiResponse<List<JobApplication>> response = new ApiResponse<>(true, "Job applications retrieved successfully", jobApplications);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<JobApplication>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<JobApplication>>> getJobApplicationsByJob(@PathVariable String jobId) {
        try {
            List<JobApplication> jobApplications = jobApplicationService.findJobApplicationsByJob(jobId);
            ApiResponse<List<JobApplication>> response = new ApiResponse<>(true, "Job applications retrieved successfully", jobApplications);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<JobApplication>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobApplication>> updateJobApplicationStatus(@PathVariable String id, @RequestParam String status) {
        try {
            JobApplication updatedJobApplication = jobApplicationService.updateJobApplicationStatus(id, status);
            ApiResponse<JobApplication> response = new ApiResponse<>(true, "Job application status updated successfully", updatedJobApplication);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<JobApplication> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJobApplication(@PathVariable String id) {
        try {
            jobApplicationService.deleteJobApplication(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Job application deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}