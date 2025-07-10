package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "employee")
public class Employee extends User {
    private String jobTitle;
    private String companyName;
    private LocalDateTime startDate;
    @ManyToOne
    private HRManager hrManager;

}