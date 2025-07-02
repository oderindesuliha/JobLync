package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Applicant extends User {
    private String resumeUrl;
    private String portfolioUrl;
    private ApplicationStatus status;
    private LocalDateTime applicationDate;
}