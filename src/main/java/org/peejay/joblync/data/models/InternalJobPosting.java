package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "internal_job_postings")
@Data
public class InternalJobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private String description;
    private String requirements;
    private String department;
    private String reportingManager;
    
    @Enumerated(EnumType.STRING)
    private JobType jobType; // FULL_TIME, PART_TIME, CONTRACT
    
    private LocalDateTime postedDate = LocalDateTime.now();
    private LocalDateTime closingDate;
    private boolean isOpen = true;
    
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;
    
    private String createdBy;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void setIsOpen(boolean b) {
    }
}