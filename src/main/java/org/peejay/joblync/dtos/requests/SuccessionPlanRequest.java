package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.peejay.joblync.data.models.SuccessionStatus;

import java.time.LocalDateTime;

@Data
public class SuccessionPlanRequest {
    private String criticalPositionId;
    private String positionTitle;
    private String department;
    private String incumbentId;
    private String successorId;
    
    @Min(value = 1, message = "Readiness level must be between 1 and 5")
    @Max(value = 5, message = "Readiness level must be between 1 and 5")
    private Integer readinessLevel;
    
    private String developmentNeeds;
    private SuccessionStatus status;
    private LocalDateTime plannedTransitionDate;
    private String notes;
}