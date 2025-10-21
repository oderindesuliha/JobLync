package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.SuccessionPlan;
import org.peejay.joblync.data.models.SuccessionStatus;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.JobPostingRepository;
import org.peejay.joblync.data.repository.SuccessionPlanRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.SuccessionPlanRequest;
import org.peejay.joblync.services.SuccessionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SuccessionPlanServiceImpl implements SuccessionPlanService {

    private final SuccessionPlanRepository successionPlanRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    @Autowired
    public SuccessionPlanServiceImpl(SuccessionPlanRepository successionPlanRepository, 
                                   UserRepository userRepository,
                                   JobPostingRepository jobPostingRepository) {
        this.successionPlanRepository = successionPlanRepository;
        this.userRepository = userRepository;
        this.jobPostingRepository = jobPostingRepository;
    }

    @Override
    public SuccessionPlan createSuccessionPlan(SuccessionPlanRequest request) {
        SuccessionPlan successionPlan = new SuccessionPlan();
        
        // Set critical position
        if (request.getCriticalPositionId() != null) {
            Optional<JobPosting> criticalPosition = jobPostingRepository.findById(request.getCriticalPositionId());
            if (criticalPosition.isPresent()) {
                successionPlan.setCriticalPosition(criticalPosition.get());
            } else {
                throw new RuntimeException("Critical position not found with id: " + request.getCriticalPositionId());
            }
        }
        
        successionPlan.setPositionTitle(request.getPositionTitle());
        successionPlan.setDepartment(request.getDepartment());
        
        // Set incumbent
        if (request.getIncumbentId() != null) {
            Optional<User> incumbent = userRepository.findById(request.getIncumbentId());
            if (incumbent.isPresent()) {
                successionPlan.setIncumbent(incumbent.get());
            } else {
                throw new RuntimeException("Incumbent not found with id: " + request.getIncumbentId());
            }
        }
        
        // Set successor
        if (request.getSuccessorId() != null) {
            Optional<User> successor = userRepository.findById(request.getSuccessorId());
            if (successor.isPresent()) {
                successionPlan.setSuccessor(successor.get());
            } else {
                throw new RuntimeException("Successor not found with id: " + request.getSuccessorId());
            }
        }
        
        successionPlan.setReadinessLevel(request.getReadinessLevel());
        successionPlan.setDevelopmentNeeds(request.getDevelopmentNeeds());
        successionPlan.setStatus(request.getStatus() != null ? request.getStatus() : SuccessionStatus.PLANNED);
        successionPlan.setPlannedTransitionDate(request.getPlannedTransitionDate());
        successionPlan.setNotes(request.getNotes());
        successionPlan.setCreatedAt(LocalDateTime.now());
        successionPlan.setUpdatedAt(LocalDateTime.now());
        
        return successionPlanRepository.save(successionPlan);
    }

    @Override
    public Optional<SuccessionPlan> findSuccessionPlanById(String id) {
        return successionPlanRepository.findById(id);
    }

    @Override
    public List<SuccessionPlan> findSuccessionPlansByIncumbent(String incumbentId) {
        Optional<User> incumbent = userRepository.findById(incumbentId);
        if (incumbent.isPresent()) {
            return successionPlanRepository.findByIncumbent(incumbent.get());
        }
        throw new RuntimeException("Incumbent not found with id: " + incumbentId);
    }

    @Override
    public List<SuccessionPlan> findSuccessionPlansBySuccessor(String successorId) {
        Optional<User> successor = userRepository.findById(successorId);
        if (successor.isPresent()) {
            return successionPlanRepository.findBySuccessor(successor.get());
        }
        throw new RuntimeException("Successor not found with id: " + successorId);
    }

    @Override
    public List<SuccessionPlan> findSuccessionPlansByDepartment(String department) {
        return successionPlanRepository.findByDepartment(department);
    }

    @Override
    public List<SuccessionPlan> findSuccessionPlansByStatus(SuccessionStatus status) {
        return successionPlanRepository.findByStatus(status);
    }

    @Override
    public List<SuccessionPlan> findSuccessionPlansByReadinessLevel(Integer minReadinessLevel) {
        return successionPlanRepository.findByReadinessLevelGreaterThanEqual(minReadinessLevel);
    }

    @Override
    public SuccessionPlan updateSuccessionPlan(String id, SuccessionPlanRequest request) {
        Optional<SuccessionPlan> existingPlan = successionPlanRepository.findById(id);
        if (existingPlan.isPresent()) {
            SuccessionPlan successionPlan = existingPlan.get();
            
            // Update critical position
            if (request.getCriticalPositionId() != null) {
                Optional<JobPosting> criticalPosition = jobPostingRepository.findById(request.getCriticalPositionId());
                if (criticalPosition.isPresent()) {
                    successionPlan.setCriticalPosition(criticalPosition.get());
                } else {
                    throw new RuntimeException("Critical position not found with id: " + request.getCriticalPositionId());
                }
            }
            
            successionPlan.setPositionTitle(request.getPositionTitle());
            successionPlan.setDepartment(request.getDepartment());
            
            // Update incumbent
            if (request.getIncumbentId() != null) {
                Optional<User> incumbent = userRepository.findById(request.getIncumbentId());
                if (incumbent.isPresent()) {
                    successionPlan.setIncumbent(incumbent.get());
                } else {
                    throw new RuntimeException("Incumbent not found with id: " + request.getIncumbentId());
                }
            }
            
            // Update successor
            if (request.getSuccessorId() != null) {
                Optional<User> successor = userRepository.findById(request.getSuccessorId());
                if (successor.isPresent()) {
                    successionPlan.setSuccessor(successor.get());
                } else {
                    throw new RuntimeException("Successor not found with id: " + request.getSuccessorId());
                }
            }
            
            successionPlan.setReadinessLevel(request.getReadinessLevel());
            successionPlan.setDevelopmentNeeds(request.getDevelopmentNeeds());
            successionPlan.setStatus(request.getStatus());
            successionPlan.setPlannedTransitionDate(request.getPlannedTransitionDate());
            successionPlan.setNotes(request.getNotes());
            successionPlan.setUpdatedAt(LocalDateTime.now());
            
            return successionPlanRepository.save(successionPlan);
        }
        throw new RuntimeException("Succession plan not found with id: " + id);
    }

    @Override
    public void deleteSuccessionPlan(String id) {
        successionPlanRepository.deleteById(id);
    }

    @Override
    public void markAsFilled(String id) {
        Optional<SuccessionPlan> existingPlan = successionPlanRepository.findById(id);
        if (existingPlan.isPresent()) {
            SuccessionPlan successionPlan = existingPlan.get();
            successionPlan.setStatus(SuccessionStatus.FILLED);
            successionPlan.setActualTransitionDate(LocalDateTime.now());
            successionPlan.setUpdatedAt(LocalDateTime.now());
            successionPlanRepository.save(successionPlan);
        } else {
            throw new RuntimeException("Succession plan not found with id: " + id);
        }
    }
}