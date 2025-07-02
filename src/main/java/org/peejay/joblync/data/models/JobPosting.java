package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class JobPosting {
    @Id
    private Long id;
    private String jobTitle;
    private String companyName;
    private String jobDescription;

    private JobStatus status;
    private LocalDateTime postDate;
    private LocalDateTime deadLine;
    @ManyToOne
    private Recruiter postedBy;


}
