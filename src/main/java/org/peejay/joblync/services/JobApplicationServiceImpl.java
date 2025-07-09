package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Applicant;
import org.peejay.joblync.data.models.ApplicationStatus;
import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.repositories.ApplicantRepository;
import org.peejay.joblync.data.repositories.JobApplicationRepository;
import org.peejay.joblync.data.repositories.JobPostingRepository;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;
import org.peejay.joblync.exceptions.JobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public void apply(JobApplicationRequest request) {
        Applicant applicant = applicantRepository.findById(request.getApplicantId())
                .orElseThrow(() -> new JobException("Applicant not found"));

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobId())
                .orElseThrow(() -> new JobException("Job posting not found"));

        JobApplication application = new JobApplication();
        application.setApplicant(applicant);
        application.setJobPosting(jobPosting);
        application.setStatus(ApplicationStatus.PENDING);
        application.setApplicationDate(LocalDateTime.now());

        jobApplicationRepository.save(application);
    }

}
