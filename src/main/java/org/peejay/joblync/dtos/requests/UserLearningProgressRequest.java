package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.peejay.joblync.data.models.CompletionStatus;

@Data
public class UserLearningProgressRequest {
    private String userId;
    private String moduleId;
    
    @Min(value = 0, message = "Progress percentage must be between 0 and 100")
    @Max(value = 100, message = "Progress percentage must be between 0 and 100")
    private Integer progressPercentage;
    
    private Boolean isCompleted;
    private CompletionStatus completionStatus;
    private Integer score;
    private String certificateUrl;
}