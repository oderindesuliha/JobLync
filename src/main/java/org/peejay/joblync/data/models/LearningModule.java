package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "learning_modules")
@Data
public class LearningModule {
    @Id
@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String title;
    private String description;
    private String provider; // Internal, LinkedIn Learning, Coursera, etc.
    private String url; // Link to external learning platform
    private Integer durationHours;
    private String category;
    
    @ElementCollection
    @CollectionTable(name = "module_skills", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "skill_id")
    private List<String> relatedSkills;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}