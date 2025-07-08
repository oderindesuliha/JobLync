package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Applicant extends User {
    private String resumeUrl;
    private String portfolioUrl;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    private LocalDateTime applicationDate;
    @ManyToOne
    private JobPosting jobPosting;

}