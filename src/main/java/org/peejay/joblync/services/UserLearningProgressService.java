package org.peejay.joblync.services;

import org.peejay.joblync.data.models.CompletionStatus;
import org.peejay.joblync.data.models.UserLearningProgress;
import org.peejay.joblync.dtos.requests.UserLearningProgressRequest;

import java.util.List;
import java.util.Optional;

public interface UserLearningProgressService {
    UserLearningProgress trackUserLearningProgress(UserLearningProgressRequest request);
    Optional<UserLearningProgress> findUserLearningProgressById(String id);
    List<UserLearningProgress> findUserLearningProgressByUser(String userId);
    List<UserLearningProgress> findUserLearningProgressByModule(String moduleId);
    List<UserLearningProgress> findUserLearningProgressByUserAndStatus(String userId, CompletionStatus status);
    UserLearningProgress updateUserLearningProgress(String id, UserLearningProgressRequest request);
    void removeUserLearningProgress(String id);
}