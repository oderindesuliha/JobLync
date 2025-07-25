package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

@Data
@Entity
@Table(name = "recruiter")
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id")public class Recruiter extends User {
    private String companyName;
    private String companyWebsite;}