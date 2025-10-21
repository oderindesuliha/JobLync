package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_skills")
@Data
public class UserSkill {
    @Id
@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;
    
    @Enumerated(EnumType.STRING)
    private SkillLevel proficiencyLevel; // Employee's actual proficiency level
    
    private Integer yearsOfExperience;
    private LocalDateTime lastAssessed = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();
}