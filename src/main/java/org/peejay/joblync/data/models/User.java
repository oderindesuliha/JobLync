package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    private String phoneNumber;
    private String password;
    private LocalDateTime dateJoined = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
   private String verificationToken;
    
    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_certifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "certification")
    private List<String> certifications = new ArrayList<>();
    
    private String department;
    private String position;
    private LocalDateTime lastPromotionDate;
    
    // Additional employee information
    private String employeeId;
   private LocalDateTime dateOfBirth;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String bankAccountNumber;
    private String bankName;
    private LocalDateTime hireDate;
    private LocalDateTime terminationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;
    
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus = EmploymentStatus.HIRED;
    
    private Double salary;
    private String jobLevel; // Junior, Mid-level, Senior, Lead, etc.
}