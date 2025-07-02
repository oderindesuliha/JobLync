package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Recruiter extends User {
    private int numberOfHires;
}