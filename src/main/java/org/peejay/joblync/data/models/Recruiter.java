package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Recruiter extends User {
    private int numberOfHires;
}