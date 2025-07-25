package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employee")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id")
public class Employee extends User {
    private String jobTitle;
    private String companyName;
    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(name = "hr_manager_id")
    private HRManager hrManager;
}
