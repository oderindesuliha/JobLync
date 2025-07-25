package org.peejay.joblync.dtos.requests;

import lombok.Data;

@Data
public class RecruiterRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePicture;
    private String companyName;
    private String companyWebsite;

}
