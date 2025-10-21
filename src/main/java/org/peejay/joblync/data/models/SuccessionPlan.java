package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "succession_plans")
@Data
public class SuccessionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private JobPosting criticalPosition;
    
    private String positionTitle;
    private String department;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incumbent_id")
    private User incumbent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "successor_id")
    private User successor;
    
    private Integer readinessLevel; // 1-5 scale (1=Not Ready, 5=Ready Now)
    private String developmentNeeds;
    
    @Enumerated(EnumType.STRING)
    private SuccessionStatus status = SuccessionStatus.PLANNED; // PLANNED, IN_PROGRESS, READY, FILLED
    
    private LocalDateTime plannedTransitionDate;
    private LocalDateTime actualTransitionDate;
    
    private String notes;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}