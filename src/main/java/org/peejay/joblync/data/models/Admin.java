package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Admin extends User {
    private String adminLevel;
}