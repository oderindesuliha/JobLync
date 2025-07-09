package org.peejay.joblync.dtos.responses;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.peejay.joblync.data.models.Role;

import java.time.LocalDateTime;

@Data
public class UserRegisterResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean isActive;
    private LocalDateTime dateJoined = LocalDateTime.now();
    private Role role;

}
