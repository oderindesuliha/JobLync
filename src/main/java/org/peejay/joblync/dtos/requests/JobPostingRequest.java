package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobPostingRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Requirements are required")
    private String requirements;
    
    @NotBlank(message = "Creator ID is required")
    private String createdBy;
    
    // Explicit getters to resolve compilation issue
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
}