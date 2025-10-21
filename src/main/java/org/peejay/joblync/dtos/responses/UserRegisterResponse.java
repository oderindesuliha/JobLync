package org.peejay.joblync.dtos.responses;


import lombok.Data;
import org.peejay.joblync.data.models.Role;

@Data
public class UserRegisterResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String message;
}