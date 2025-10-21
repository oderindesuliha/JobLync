package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.peejay.joblync.data.models.SkillLevel;

@Data
public class SkillRequest {
    @NotBlank(message = "Skill name is required")
    private String name;
    
    private String description;
    private String category;
    private SkillLevel requiredLevel;
}