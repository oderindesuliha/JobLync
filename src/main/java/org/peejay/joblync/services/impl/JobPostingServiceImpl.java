package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.repository.JobPostingRepository;
import org.peejay.joblync.dtos.requests.JobPostingRequest;
import org.peejay.joblync.services.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    @Autowired
    public JobPostingServiceImpl(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    @Override
    public JobPosting createJobPosting(JobPostingRequest jobPostingRequest) {
        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle(jobPostingRequest.getTitle());
        jobPosting.setDescription(jobPostingRequest.getDescription());
        jobPosting.setRequirements(jobPostingRequest.getRequirements());
        jobPosting.setCreatedBy(jobPostingRequest.getCreatedBy());
        jobPosting.setCreatedAt(LocalDateTime.now());
        jobPosting.setUpdatedAt(LocalDateTime.now());
        
        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public Optional<JobPosting> findJobPostingById(String id) {
        return jobPostingRepository.findById(id);
    }

    @Override
    public List<JobPosting> findAllJobPostings() {
        return jobPostingRepository.findAll();
    }

    @Override
    public List<JobPosting> findJobPostingsByCreator(String creatorId) {
        return jobPostingRepository.findByCreatedBy(creatorId);
    }

    @Override
    public JobPosting updateJobPosting(String id, JobPostingRequest jobPostingRequest) {
        Optional<JobPosting> existingJobPosting = jobPostingRepository.findById(id);
        
        if (existingJobPosting.isPresent()) {
            JobPosting jobPosting = existingJobPosting.get();
            jobPosting.setTitle(jobPostingRequest.getTitle());
            jobPosting.setDescription(jobPostingRequest.getDescription());
            jobPosting.setRequirements(jobPostingRequest.getRequirements());
            jobPosting.setUpdatedAt(LocalDateTime.now());
            
            return jobPostingRepository.save(jobPosting);
        } else {
            throw new RuntimeException("Job posting not found with id: " + id);
        }
    }

    @Override
    public void deleteJobPosting(String id) {
        if (jobPostingRepository.existsById(id)) {
            jobPostingRepository.deleteById(id);
        } else {
            throw new RuntimeException("Job posting not found with id: " + id);
        }
    }
}