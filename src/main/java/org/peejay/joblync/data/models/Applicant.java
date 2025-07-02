package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Applicant extends User {
    @Id
    private Long candidateId;
    private String resumeUrl;
    private String portfolioUrl;
    private String status;
    private LocalDateTime applicationDate;


}


