package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.JobType;

import java.time.LocalDateTime;

@Data
public class InternalJobPostingRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Requirements are required")
    private String requirements;
    
    private String department;
    private String reportingManager;
    private JobType jobType;
    private LocalDateTime closingDate;
    private ExperienceLevel experienceLevel;
}