package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository <JobPosting, String> {
    List<JobPosting> findByCompanyName(String companyName);

    List<JobPosting> findByCompanyNameAndJobTitle(String companyName, String jobTitle);

    Optional<JobPosting> findJobPostingByStatus(JobStatus status);

}
