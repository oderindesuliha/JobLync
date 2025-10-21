package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.CompletionStatus;
import org.peejay.joblync.data.models.LearningModule;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.models.UserLearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLearningProgressRepository extends JpaRepository<UserLearningProgress, String> {
    List<UserLearningProgress> findByUser(User user);
    List<UserLearningProgress> findByLearningModule(LearningModule learningModule);
    List<UserLearningProgress> findByUserAndLearningModule(User user, LearningModule learningModule);
    List<UserLearningProgress> findByUserAndCompletionStatus(User user, CompletionStatus completionStatus);
    Optional<UserLearningProgress> findByUserIdAndLearningModuleId(String userId, String learningModuleId);
}