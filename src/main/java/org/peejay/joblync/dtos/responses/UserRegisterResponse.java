package org.peejay.joblync.dtos.responses;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.peejay.joblync.data.models.Role;

@Data
public class UserRegisterResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Role role;
    private String about;
}
