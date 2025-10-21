package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.peejay.joblync.data.models.ReviewType;

import java.time.LocalDateTime;

@Data
public class PerformanceReviewRequest {
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    
    private String reviewerId;
    
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;
    
    private String comments;
    private String goals;
    private String areasForImprovement;
    private ReviewType reviewType;
    private LocalDateTime nextReviewDate;
}