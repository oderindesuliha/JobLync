package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, String> {
    List<JobPosting> findByCreatedBy(String createdBy);
}