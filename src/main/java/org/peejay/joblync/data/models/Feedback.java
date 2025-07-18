package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private  HRManager manager;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    private String comments;
    private LocalDateTime feedbackDate;
}
