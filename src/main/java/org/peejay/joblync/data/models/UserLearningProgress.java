package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_learning_progress")
@Data
public class UserLearningProgress {
    @Id
@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private LearningModule learningModule;
    
    private Integer progressPercentage = 0; // 0-100
    private Boolean isCompleted = false;
    
    @Enumerated(EnumType.STRING)
    private CompletionStatus completionStatus = CompletionStatus.NOT_STARTED; // NOT_STARTED, IN_PROGRESS, COMPLETED
    
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private Integer score; // If applicable
    private String certificateUrl; // If applicable
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}