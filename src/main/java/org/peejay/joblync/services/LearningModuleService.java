package org.peejay.joblync.services;

import org.peejay.joblync.data.models.LearningModule;
import org.peejay.joblync.dtos.requests.LearningModuleRequest;

import java.util.List;
import java.util.Optional;

public interface LearningModuleService {
    LearningModule createLearningModule(LearningModuleRequest request);
    Optional<LearningModule> findLearningModuleById(String id);
    List<LearningModule> findAllLearningModules();
    List<LearningModule> findLearningModulesByCategory(String category);
    List<LearningModule> findLearningModulesByProvider(String provider);
    LearningModule updateLearningModule(String id, LearningModuleRequest request);
    void deleteLearningModule(String id);
}