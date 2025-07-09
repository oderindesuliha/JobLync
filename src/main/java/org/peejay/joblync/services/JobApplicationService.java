package org.peejay.joblync.services;

import org.peejay.joblync.data.repositories.ApplicantRepository;
import org.peejay.joblync.data.repositories.JobApplicationRepository;
import org.peejay.joblync.data.repositories.JobPostingRepository;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;

public interface JobApplicationService {

    void apply(JobApplicationRequest request);
}
