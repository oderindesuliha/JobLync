package org.peejay.joblync.services;

import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.InternalJobPosting;
import org.peejay.joblync.dtos.requests.InternalJobPostingRequest;

import java.util.List;
import java.util.Optional;

public interface InternalJobPostingService {
    InternalJobPosting createInternalJobPosting(InternalJobPostingRequest request, String creatorId);
    Optional<InternalJobPosting> findInternalJobPostingById(String id);
    List<InternalJobPosting> findAllInternalJobPostings();
    List<InternalJobPosting> findOpenInternalJobPostings();
    List<InternalJobPosting> findInternalJobPostingsByDepartment(String department);
    List<InternalJobPosting> findInternalJobPostingsByExperienceLevel(ExperienceLevel experienceLevel);
    InternalJobPosting updateInternalJobPosting(String id, InternalJobPostingRequest request);
    void deleteInternalJobPosting(String id);
    void closeInternalJobPosting(String id);
}