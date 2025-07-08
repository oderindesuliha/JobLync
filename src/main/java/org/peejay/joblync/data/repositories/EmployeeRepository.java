package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



public interface EmployeeRepository extends JpaRepository <Employee, Long>{
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    EmployeeRepository findByEmail(String email);
    List<EmployeeRepository> findByJobTitle(String jobTitle);
    Optional<EmployeeRepository> findByEmailAndCompanyName(String email, String companyName);
}
