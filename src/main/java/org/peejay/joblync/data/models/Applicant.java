package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "applicant")
@EqualsAndHashCode(callSuper = true)
public class Applicant extends User {
    private String resumeUrl;
    private String portfolioUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

}