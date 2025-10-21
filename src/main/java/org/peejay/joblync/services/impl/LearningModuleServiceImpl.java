package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.LearningModule;
import org.peejay.joblync.data.repository.LearningModuleRepository;
import org.peejay.joblync.dtos.requests.LearningModuleRequest;
import org.peejay.joblync.services.LearningModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LearningModuleServiceImpl implements LearningModuleService {

    private final LearningModuleRepository learningModuleRepository;

    @Autowired
    public LearningModuleServiceImpl(LearningModuleRepository learningModuleRepository) {
        this.learningModuleRepository = learningModuleRepository;
    }

    @Override
    public LearningModule createLearningModule(LearningModuleRequest request) {
        LearningModule learningModule = new LearningModule();
        learningModule.setTitle(request.getTitle());
        learningModule.setDescription(request.getDescription());
        learningModule.setProvider(request.getProvider());
        learningModule.setUrl(request.getUrl());
        learningModule.setDurationHours(request.getDurationHours());
        learningModule.setCategory(request.getCategory());
        learningModule.setRelatedSkills(request.getRelatedSkills());
        learningModule.setCreatedAt(LocalDateTime.now());
        learningModule.setUpdatedAt(LocalDateTime.now());
        
        return learningModuleRepository.save(learningModule);
    }

    @Override
    public Optional<LearningModule> findLearningModuleById(String id) {
        return learningModuleRepository.findById(id);
    }

    @Override
    public List<LearningModule> findAllLearningModules() {
        return learningModuleRepository.findAll();
    }

    @Override
    public List<LearningModule> findLearningModulesByCategory(String category) {
        return learningModuleRepository.findByCategory(category);
    }

    @Override
    public List<LearningModule> findLearningModulesByProvider(String provider) {
        return learningModuleRepository.findByProvider(provider);
    }

    @Override
    public LearningModule updateLearningModule(String id, LearningModuleRequest request) {
        Optional<LearningModule> existingModule = learningModuleRepository.findById(id);
        if (existingModule.isPresent()) {
            LearningModule learningModule = existingModule.get();
            learningModule.setTitle(request.getTitle());
            learningModule.setDescription(request.getDescription());
            learningModule.setProvider(request.getProvider());
            learningModule.setUrl(request.getUrl());
            learningModule.setDurationHours(request.getDurationHours());
            learningModule.setCategory(request.getCategory());
            learningModule.setRelatedSkills(request.getRelatedSkills());
            learningModule.setUpdatedAt(LocalDateTime.now());
            
            return learningModuleRepository.save(learningModule);
        }
        throw new RuntimeException("Learning module not found with id: " + id);
    }

    @Override
    public void deleteLearningModule(String id) {
        learningModuleRepository.deleteById(id);
    }
}