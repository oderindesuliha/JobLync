package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.dtos.requests.SkillRequest;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    Skill createSkill(SkillRequest request);
    Optional<Skill> findSkillById(String id);
    Optional<Skill> findSkillByName(String name);
    List<Skill> findAllSkills();
    List<Skill> findSkillsByCategory(String category);
    Skill updateSkill(String id, SkillRequest request);
    void deleteSkill(String id);
}