package org.peejay.joblync.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository <EmployeeRepository, Long>{
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    EmployeeRepository findByEmail(String email);
    List<EmployeeRepository> findByJobTitle(String jobTitle);
    Optional<EmployeeRepository> findByEmailAndCompanyName(String email, String companyName);
}
