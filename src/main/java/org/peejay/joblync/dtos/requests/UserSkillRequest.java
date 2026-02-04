package org.peejay.joblync.dtos.requests;

import lombok.Data;
import org.peejay.joblync.data.models.SkillLevel;

import jakarta.validation.constraints.NotNull;

@Data
public class UserSkillRequest {
    @NotNull(message = "User ID is required")
    private String userId;
    
    @NotNull(message = "Skill ID is required")
    private String skillId;
    
    @NotNull(message = "Proficiency level is required")
    private SkillLevel proficiencyLevel;
    
    private Integer yearsOfExperience;
}