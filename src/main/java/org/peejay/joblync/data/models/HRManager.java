package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class HRManager extends User {
    private String teamName;
    private boolean isSenior;
    @OneToMany(mappedBy = "hrManager", cascade = jakarta.persistence.CascadeType.ALL)
    private List<Employee> employees;

}