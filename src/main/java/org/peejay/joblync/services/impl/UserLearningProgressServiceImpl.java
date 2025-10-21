package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.CompletionStatus;
import org.peejay.joblync.data.models.LearningModule;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.models.UserLearningProgress;
import org.peejay.joblync.data.repository.LearningModuleRepository;
import org.peejay.joblync.data.repository.UserLearningProgressRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.UserLearningProgressRequest;
import org.peejay.joblync.services.UserLearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserLearningProgressServiceImpl implements UserLearningProgressService {

    private final UserLearningProgressRepository userLearningProgressRepository;
    private final UserRepository userRepository;
    private final LearningModuleRepository learningModuleRepository;

    @Autowired
    public UserLearningProgressServiceImpl(UserLearningProgressRepository userLearningProgressRepository,
                                         UserRepository userRepository,
                                         LearningModuleRepository learningModuleRepository) {
        this.userLearningProgressRepository = userLearningProgressRepository;
        this.userRepository = userRepository;
        this.learningModuleRepository = learningModuleRepository;
    }

    @Override
    public UserLearningProgress trackUserLearningProgress(UserLearningProgressRequest request) {
        if (request.getUserId() != null && request.getModuleId() != null) {
            Optional<UserLearningProgress> existingProgress = userLearningProgressRepository
                    .findByUserIdAndLearningModuleId(request.getUserId(), request.getModuleId());
            if (existingProgress.isPresent()) {
                return updateUserLearningProgress(existingProgress.get().getId(), request);
            }
        }
        
        UserLearningProgress userLearningProgress = new UserLearningProgress();
        
        // Set user
        if (request.getUserId() != null) {
            Optional<User> user = userRepository.findById(request.getUserId());
            if (user.isPresent()) {
                userLearningProgress.setUser(user.get());
            } else {
                throw new RuntimeException("User not found with id: " + request.getUserId());
            }
        }
        
        // Set learning module
        if (request.getModuleId() != null) {
            Optional<LearningModule> learningModule = learningModuleRepository.findById(request.getModuleId());
            if (learningModule.isPresent()) {
                userLearningProgress.setLearningModule(learningModule.get());
            } else {
                throw new RuntimeException("Learning module not found with id: " + request.getModuleId());
            }
        }
        
        userLearningProgress.setProgressPercentage(request.getProgressPercentage() != null ? 
                request.getProgressPercentage() : 0);
        userLearningProgress.setIsCompleted(request.getIsCompleted() != null ? 
                request.getIsCompleted() : false);
        userLearningProgress.setCompletionStatus(request.getCompletionStatus() != null ? 
                request.getCompletionStatus() : CompletionStatus.NOT_STARTED);
        userLearningProgress.setScore(request.getScore());
        userLearningProgress.setCertificateUrl(request.getCertificateUrl());
        userLearningProgress.setCreatedAt(LocalDateTime.now());
        userLearningProgress.setUpdatedAt(LocalDateTime.now());
        
        // Set dates based on status
        if (userLearningProgress.getCompletionStatus() == CompletionStatus.IN_PROGRESS && 
                userLearningProgress.getStartDate() == null) {
            userLearningProgress.setStartDate(LocalDateTime.now());
        }
        
        if (userLearningProgress.getCompletionStatus() == CompletionStatus.COMPLETED && 
                userLearningProgress.getCompletionDate() == null) {
            userLearningProgress.setCompletionDate(LocalDateTime.now());
        }
        
        return userLearningProgressRepository.save(userLearningProgress);
    }

    @Override
    public Optional<UserLearningProgress> findUserLearningProgressById(String id) {
        return userLearningProgressRepository.findById(id);
    }

    @Override
    public List<UserLearningProgress> findUserLearningProgressByUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return userLearningProgressRepository.findByUser(user.get());
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    @Override
    public List<UserLearningProgress> findUserLearningProgressByModule(String moduleId) {
        Optional<LearningModule> learningModule = learningModuleRepository.findById(moduleId);
        if (learningModule.isPresent()) {
            return userLearningProgressRepository.findByLearningModule(learningModule.get());
        }
        throw new RuntimeException("Learning module not found with id: " + moduleId);
    }

    @Override
    public List<UserLearningProgress> findUserLearningProgressByUserAndStatus(String userId, CompletionStatus status) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return userLearningProgressRepository.findByUserAndCompletionStatus(user.get(), status);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    @Override
    public UserLearningProgress updateUserLearningProgress(String id, UserLearningProgressRequest request) {
        Optional<UserLearningProgress> existingProgress = userLearningProgressRepository.findById(id);
        if (existingProgress.isPresent()) {
            UserLearningProgress userLearningProgress = existingProgress.get();
            
            // Update user if provided
            if (request.getUserId() != null) {
                Optional<User> user = userRepository.findById(request.getUserId());
                if (user.isPresent()) {
                    userLearningProgress.setUser(user.get());
                } else {
                    throw new RuntimeException("User not found with id: " + request.getUserId());
                }
            }
            
            // Update learning module if provided
            if (request.getModuleId() != null) {
                Optional<LearningModule> learningModule = learningModuleRepository.findById(request.getModuleId());
                if (learningModule.isPresent()) {
                    userLearningProgress.setLearningModule(learningModule.get());
                } else {
                    throw new RuntimeException("Learning module not found with id: " + request.getModuleId());
                }
            }
            
            if (request.getProgressPercentage() != null) {
                userLearningProgress.setProgressPercentage(request.getProgressPercentage());
            }
            
            if (request.getIsCompleted() != null) {
                userLearningProgress.setIsCompleted(request.getIsCompleted());
            }
            
            if (request.getCompletionStatus() != null) {
                userLearningProgress.setCompletionStatus(request.getCompletionStatus());
                
                // Set dates based on status
                if (request.getCompletionStatus() == CompletionStatus.IN_PROGRESS && 
                        userLearningProgress.getStartDate() == null) {
                    userLearningProgress.setStartDate(LocalDateTime.now());
                }
                
                if (request.getCompletionStatus() == CompletionStatus.COMPLETED && 
                        userLearningProgress.getCompletionDate() == null) {
                    userLearningProgress.setCompletionDate(LocalDateTime.now());
                }
            }
            
            userLearningProgress.setScore(request.getScore());
            userLearningProgress.setCertificateUrl(request.getCertificateUrl());
            userLearningProgress.setUpdatedAt(LocalDateTime.now());
            
            return userLearningProgressRepository.save(userLearningProgress);
        }
        throw new RuntimeException("User learning progress not found with id: " + id);
    }

    @Override
    public void removeUserLearningProgress(String id) {
        userLearningProgressRepository.deleteById(id);
    }
}