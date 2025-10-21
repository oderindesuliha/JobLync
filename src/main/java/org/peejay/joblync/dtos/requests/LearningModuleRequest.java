package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class LearningModuleRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    private String provider;
    private String url;
    private Integer durationHours;
    private String category;
    private List<String> relatedSkills;
    
    // Explicit getters and setters to resolve compilation issue
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public String getUrl() {
        return url;
    }
    
    public Integer getDurationHours() {
        return durationHours;
    }
    
    public String getCategory() {
        return category;
    }
    
    public List<String> getRelatedSkills() {
        return relatedSkills;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setRelatedSkills(List<String> relatedSkills) {
        this.relatedSkills = relatedSkills;
    }
}