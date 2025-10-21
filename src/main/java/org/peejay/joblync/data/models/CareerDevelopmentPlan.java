package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "career_development_plans")
@Data
public class CareerDevelopmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private User employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;
    
    private String currentPosition;
    private String targetPosition;
    private String department;
    
    @ElementCollection
    @CollectionTable(name = "development_goals", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "goal")
    private List<String> developmentGoals;
    
    @ElementCollection
    @CollectionTable(name = "required_skills", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "skill")
    private List<String> requiredSkills;
    
    @ElementCollection
    @CollectionTable(name = "training_activities", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "activity")
    private List<String> trainingActivities;
    
    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime targetCompletionDate;
    private LocalDateTime actualCompletionDate;
    
    @Enumerated(EnumType.STRING)
    private PlanStatus status = PlanStatus.DRAFT; // DRAFT, APPROVED, IN_PROGRESS, COMPLETED, CANCELLED
}