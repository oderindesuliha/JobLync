package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.Applicant;
import org.peejay.joblync.data.models.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Applicant findByEmail(String email);
    List<Applicant> findByStatus(ApplicationStatus status);
    List<Applicant> findByJobTitle(String jobTitle);




}
