package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {
    Optional<Skill> findByName(String name);
    boolean existsByName(String name);
}