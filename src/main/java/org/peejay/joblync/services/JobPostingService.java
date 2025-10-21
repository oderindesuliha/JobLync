package org.peejay.joblync.services;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.dtos.requests.JobPostingRequest;

import java.util.List;
import java.util.Optional;

public interface JobPostingService {
    JobPosting createJobPosting(JobPostingRequest jobPostingRequest);
    Optional<JobPosting> findJobPostingById(String id);
    List<JobPosting> findAllJobPostings();
    List<JobPosting> findJobPostingsByCreator(String creatorId);
    JobPosting updateJobPosting(String id, JobPostingRequest jobPostingRequest);
    void deleteJobPosting(String id);
}