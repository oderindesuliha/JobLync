package org.peejay.joblync.services;

import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;

import java.util.List;
import java.util.Optional;

public interface JobApplicationService {
    JobApplication applyForJob(JobApplicationRequest jobApplicationRequest);
    Optional<JobApplication> findJobApplicationById(String id);
    List<JobApplication> findJobApplicationsByApplicant(String applicantId);
    List<JobApplication> findJobApplicationsByJob(String jobId);
    List<JobApplication> findJobApplicationsByStatus(String status);
    JobApplication updateJobApplicationStatus(String id, String status);
    void deleteJobApplication(String id);
    boolean hasUserAppliedForJob(String userId, String jobId);
}