package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.Status;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.JobApplicationRepository;
import org.peejay.joblync.data.repository.JobPostingRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;
import org.peejay.joblync.services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, 
                                   UserRepository userRepository, 
                                   JobPostingRepository jobPostingRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    @Override
    public JobApplication applyForJob(JobApplicationRequest jobApplicationRequest) {
        // Find applicant
        Optional<User> applicant = userRepository.findById(jobApplicationRequest.getApplicantId());
        if (applicant.isEmpty()) {
            throw new RuntimeException("Applicant not found with id: " + Objects.toString(jobApplicationRequest.getApplicantId(), "null"));
        }

        // Find job posting
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(jobApplicationRequest.getJobPostingId());
        if (jobPosting.isEmpty()) {
            throw new RuntimeException("Job posting not found with id: " + Objects.toString(jobApplicationRequest.getJobPostingId(), "null"));
        }

        // Create job application
        JobApplication application = new JobApplication();
        application.setApplicant(applicant.get());
        application.setJob(jobPosting.get());
        application.setStatus(Status.PENDING);
        application.setAppliedAt(LocalDateTime.now());

        return jobApplicationRepository.save(application);
    }

    @Override
    public Optional<JobApplication> findJobApplicationById(String id) {
        return jobApplicationRepository.findById(id);
    }

    @Override
    public List<JobApplication> findJobApplicationsByApplicant(String applicantId) {
        Optional<User> applicant = userRepository.findById(applicantId);
        if (applicant.isPresent()) {
            return jobApplicationRepository.findByApplicant(applicant.get());
        } else {
            throw new RuntimeException("Applicant not found with id: " + Objects.toString(applicantId, "null"));
        }
    }

    @Override
    public List<JobApplication> findJobApplicationsByJob(String jobId) {
        Optional<JobPosting> job = jobPostingRepository.findById(jobId);
        if (job.isPresent()) {
            return jobApplicationRepository.findByJob(job.get());
        } else {
            throw new RuntimeException("Job not found with id: " + Objects.toString(jobId, "null"));
        }
    }

    @Override
    public List<JobApplication> findJobApplicationsByStatus(String status) {
        try {
            Status applicationStatus = Status.valueOf(status.toUpperCase());
            return jobApplicationRepository.findByStatus(applicationStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + Objects.toString(status, "null"));
        }
    }

    @Override
    public JobApplication updateJobApplicationStatus(String id, String status) {
        Optional<JobApplication> existingApplication = jobApplicationRepository.findById(id);
        
        if (existingApplication.isPresent()) {
            try {
                Status newStatus = Status.valueOf(status.toUpperCase());
                JobApplication application = existingApplication.get();
                application.setStatus(newStatus);
                
                return jobApplicationRepository.save(application);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status: " + Objects.toString(status, "null"));
            }
        } else {
            throw new RuntimeException("Job application not found with id: " + Objects.toString(id, "null"));
        }
    }

    @Override
    public void deleteJobApplication(String id) {
        if (jobApplicationRepository.existsById(id)) {
            jobApplicationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Job application not found with id: " + Objects.toString(id, "null"));
        }
    }

    @Override
    public boolean hasUserAppliedForJob(String userId, String jobId) {
        Optional<User> applicant = userRepository.findById(userId);
        Optional<JobPosting> job = jobPostingRepository.findById(jobId);
        
        if (applicant.isPresent() && job.isPresent()) {
            return jobApplicationRepository.existsByApplicantAndJob(applicant.get(), job.get());
        }
        
        return false;
    }
}