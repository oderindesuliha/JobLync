package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.InternalJobPosting;
import org.peejay.joblync.data.repository.InternalJobPostingRepository;
import org.peejay.joblync.dtos.requests.InternalJobPostingRequest;
import org.peejay.joblync.services.InternalJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class InternalJobPostingServiceImpl implements InternalJobPostingService {

    private final InternalJobPostingRepository internalJobPostingRepository;

    @Autowired
    public InternalJobPostingServiceImpl(InternalJobPostingRepository internalJobPostingRepository) {
        this.internalJobPostingRepository = internalJobPostingRepository;
    }

    @Override
    public InternalJobPosting createInternalJobPosting(InternalJobPostingRequest request, String creatorId) {
        InternalJobPosting internalJobPosting = new InternalJobPosting();
        internalJobPosting.setTitle(request.getTitle());
        internalJobPosting.setDescription(request.getDescription());
        internalJobPosting.setRequirements(request.getRequirements());
        internalJobPosting.setDepartment(request.getDepartment());
        internalJobPosting.setReportingManager(request.getReportingManager());
        internalJobPosting.setJobType(request.getJobType());
        internalJobPosting.setClosingDate(request.getClosingDate());
        internalJobPosting.setExperienceLevel(request.getExperienceLevel());
        internalJobPosting.setCreatedBy(creatorId);
        internalJobPosting.setCreatedAt(LocalDateTime.now());
        internalJobPosting.setUpdatedAt(LocalDateTime.now());
        
        return internalJobPostingRepository.save(internalJobPosting);
    }

    @Override
    public Optional<InternalJobPosting> findInternalJobPostingById(String id) {
        return internalJobPostingRepository.findById(id);
    }

    @Override
    public List<InternalJobPosting> findAllInternalJobPostings() {
        return internalJobPostingRepository.findAll();
    }

    @Override
    public List<InternalJobPosting> findOpenInternalJobPostings() {
        return internalJobPostingRepository.findByIsOpen(true);
    }

    @Override
    public List<InternalJobPosting> findInternalJobPostingsByDepartment(String department) {
        return internalJobPostingRepository.findByDepartment(department);
    }

    @Override
    public List<InternalJobPosting> findInternalJobPostingsByExperienceLevel(ExperienceLevel experienceLevel) {
        return internalJobPostingRepository.findByExperienceLevel(experienceLevel);
    }

    @Override
    public InternalJobPosting updateInternalJobPosting(String id, InternalJobPostingRequest request) {
        Optional<InternalJobPosting> existingPosting = internalJobPostingRepository.findById(id);
        if (existingPosting.isPresent()) {
            InternalJobPosting internalJobPosting = existingPosting.get();
            internalJobPosting.setTitle(request.getTitle());
            internalJobPosting.setDescription(request.getDescription());
            internalJobPosting.setRequirements(request.getRequirements());
            internalJobPosting.setDepartment(request.getDepartment());
            internalJobPosting.setReportingManager(request.getReportingManager());
            internalJobPosting.setJobType(request.getJobType());
            internalJobPosting.setClosingDate(request.getClosingDate());
            internalJobPosting.setExperienceLevel(request.getExperienceLevel());
            internalJobPosting.setUpdatedAt(LocalDateTime.now());
            
            return internalJobPostingRepository.save(internalJobPosting);
        }
        throw new RuntimeException("Internal job posting not found with id: " + Objects.toString(id, "null"));
    }

    @Override
    public void deleteInternalJobPosting(String id) {
        internalJobPostingRepository.deleteById(id);
    }

    @Override
    public void closeInternalJobPosting(String id) {
        Optional<InternalJobPosting> existingPosting = internalJobPostingRepository.findById(id);
        if (existingPosting.isPresent()) {
            InternalJobPosting internalJobPosting = existingPosting.get();
            internalJobPosting.setIsOpen(false);
            internalJobPosting.setUpdatedAt(LocalDateTime.now());
            internalJobPostingRepository.save(internalJobPosting);
        } else {
            throw new RuntimeException("Internal job posting not found with id: " + Objects.toString(id, "null"));
        }
    }
}