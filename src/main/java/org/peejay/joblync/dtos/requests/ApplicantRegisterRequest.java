package org.peejay.joblync.dtos.requests;

import lombok.Data;
import org.peejay.joblync.data.models.ApplicationStatus;

@Data
public class ApplicantRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePicture;
    private String resumeUrl;
    private String portfolioUrl;
    private ApplicationStatus status;




}
