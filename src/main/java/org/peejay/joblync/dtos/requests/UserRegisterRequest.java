package org.peejay.joblync.dtos.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.peejay.joblync.data.models.Role;

@Data
public class UserRegisterRequest{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
}
