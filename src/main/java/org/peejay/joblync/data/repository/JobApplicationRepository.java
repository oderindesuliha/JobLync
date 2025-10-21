package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.Status;
import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, String> {
    List<JobApplication> findByApplicant(User applicant);
    List<JobApplication> findByJob(JobPosting job);
    List<JobApplication> findByApplicantAndJob(User applicant, JobPosting job);
    List<JobApplication> findByStatus(Status status);
    boolean existsByApplicantAndJob(User applicant, JobPosting job);
}