package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {
    @Id
@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private String description;
    private String category; // Technical, Soft, Leadership, etc.
    
    @Enumerated(EnumType.STRING)
    private SkillLevel requiredLevel = SkillLevel.BASIC; // BASIC, INTERMEDIATE, ADVANCED, EXPERT
}