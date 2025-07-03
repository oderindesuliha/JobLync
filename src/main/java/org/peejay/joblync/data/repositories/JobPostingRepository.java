package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository <JobPosting, Long>{
    List<JobPosting> findByCompanyName(String companyName);
    List<JobPosting> findByCompanyNameAndJobName(String companyName, String jobName);
    Optional<JobPosting> findByStatus(String status);

    List<JobPosting> findByCompanyNameAndJobTitle(String techCorps, String backendDeveloper);
}
