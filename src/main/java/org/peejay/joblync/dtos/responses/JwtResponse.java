package org.peejay.joblync.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String jwtToken;
    private String email;
    private List<String> roles;
    private String message;

}