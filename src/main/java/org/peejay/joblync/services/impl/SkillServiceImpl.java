package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.data.repository.SkillRepository;
import org.peejay.joblync.dtos.requests.SkillRequest;
import org.peejay.joblync.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill createSkill(SkillRequest request) {
        // Check if skill already exists
        if (skillRepository.existsByName(request.getName())) {
            throw new RuntimeException("Skill with name " + request.getName() + " already exists");
        }
        
        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());
        skill.setRequiredLevel(request.getRequiredLevel());
        
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> findSkillById(String id) {
        return skillRepository.findById(id);
    }

    @Override
    public Optional<Skill> findSkillByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public List<Skill> findAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public List<Skill> findSkillsByCategory(String category) {
        // This would require a custom query method in the repository
        // For now, we'll filter in memory
        return skillRepository.findAll().stream()
                .filter(skill -> category.equals(skill.getCategory()))
                .toList();
    }

    @Override
    public Skill updateSkill(String id, SkillRequest request) {
        Optional<Skill> existingSkill = skillRepository.findById(id);
        if (existingSkill.isPresent()) {
            Skill skill = existingSkill.get();
            skill.setName(request.getName());
            skill.setDescription(request.getDescription());
            skill.setCategory(request.getCategory());
            skill.setRequiredLevel(request.getRequiredLevel());
            
            return skillRepository.save(skill);
        }
        throw new RuntimeException("Skill not found with id: " + id);
    }

    @Override
    public void deleteSkill(String id) {
        skillRepository.deleteById(id);
    }
}