package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobApplicationRequest {
    @NotBlank(message = "Applicant ID is required")
    private String applicantId;
    
    @NotBlank(message = "Job Posting ID is required")
    private String jobPostingId;
}