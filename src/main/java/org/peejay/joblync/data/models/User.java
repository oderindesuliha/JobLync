package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
    private String profilePicture;
    private LocalDateTime dateJoined;
    private boolean isActive = true;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Applicant applicant;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Recruiter recruiter;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private HR_Manager hiringManager;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

}
