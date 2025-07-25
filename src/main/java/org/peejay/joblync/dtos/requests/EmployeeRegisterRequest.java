package org.peejay.joblync.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePicture;
    private String jobTitle;
    private String companyName;
    private LocalDateTime startDate;
    private Long hrManagerId;
}



