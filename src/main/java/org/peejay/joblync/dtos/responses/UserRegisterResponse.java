package org.peejay.joblync.dtos.responses;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean isActive;
    private LocalDateTime dateJoined = LocalDateTime.now();
    private Role role;

    public UserRegisterResponse(User user) {
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.isActive = user.isActive();
        this.dateJoined = user.getDateJoined();
        this.role = user.getRole();
    }
}
