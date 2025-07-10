package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String companyName;
    private String jobDescription;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDateTime postDate;
    private LocalDateTime deadLine;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter postedBy;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> applications;
}
