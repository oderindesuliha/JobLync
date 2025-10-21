package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.LearningModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningModuleRepository extends JpaRepository<LearningModule, String> {
    List<LearningModule> findByCategory(String category);
    List<LearningModule> findByProvider(String provider);
    Optional<LearningModule> findByTitle(String title);
}