package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String department;
    private String position;

    private LocalDateTime hireDate;

    private LocalDateTime dateJoined = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus = EmploymentStatus.HIRED;

    private String jobLevel; // Junior, Mid-level, Senior, Lead, etc.
}