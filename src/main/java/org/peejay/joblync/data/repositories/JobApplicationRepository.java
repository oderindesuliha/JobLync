package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.ApplicationStatus;
import org.peejay.joblync.data.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(ApplicationStatus status);


}
