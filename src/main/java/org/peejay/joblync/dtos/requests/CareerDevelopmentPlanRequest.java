package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.peejay.joblync.data.models.PlanStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CareerDevelopmentPlanRequest {
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    
    private String managerId;
    private String currentPosition;
    private String targetPosition;
    private String department;
    private List<String> developmentGoals;
    private List<String> requiredSkills;
    private List<String> trainingActivities;
    private LocalDateTime targetCompletionDate;
    private PlanStatus status;
}