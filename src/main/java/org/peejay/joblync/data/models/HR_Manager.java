package org.peejay.joblync.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class HR_Manager extends User {
    private String teamName;
    private boolean isSenior;
    @OneToMany(mappedBy = "hrManager")
    private List<Employee> employees;

}