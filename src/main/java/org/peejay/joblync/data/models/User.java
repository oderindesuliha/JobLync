package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
    private String profilePicture;
    private LocalDateTime dateJoined;
    private boolean isActive = true;
}