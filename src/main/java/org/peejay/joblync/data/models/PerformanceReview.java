package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "performance_reviews")
@Data
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private User employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    
    private Integer rating; // 1-5 scale
    private String comments;
    private String goals;
    private String areasForImprovement;
    
    @Enumerated(EnumType.STRING)
    private ReviewType reviewType; // ANNUAL, QUARTERLY, MID_YEAR, PEER, SELF
    
    private LocalDateTime reviewDate = LocalDateTime.now();
    private LocalDateTime nextReviewDate;
}