package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Recruiter findByEmail(String email);
}
