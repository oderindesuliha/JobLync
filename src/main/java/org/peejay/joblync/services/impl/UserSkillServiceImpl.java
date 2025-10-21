package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.data.models.SkillLevel;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.models.UserSkill;
import org.peejay.joblync.data.repository.SkillRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.data.repository.UserSkillRepository;
import org.peejay.joblync.dtos.requests.UserSkillRequest;
import org.peejay.joblync.services.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public UserSkillServiceImpl(UserSkillRepository userSkillRepository, 
                              UserRepository userRepository,
                              SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public UserSkill addUserSkill(UserSkillRequest request) {
        // Check if user-skill combination already exists
        Optional<UserSkill> existingUserSkill = userSkillRepository.findByUserIdAndSkillId(
                String.valueOf(request.getUserId()), request.getSkillId());
        if (existingUserSkill.isPresent()) {
            throw new RuntimeException("User already has this skill recorded");
        }
        
        UserSkill userSkill = new UserSkill();
        
        // Set user
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isPresent()) {
            userSkill.setUser(user.get());
        } else {
            throw new RuntimeException("User not found with id: " + request.getUserId());
        }
        
        // Set skill
        Optional<Skill> skill = skillRepository.findById(request.getSkillId());
        if (skill.isPresent()) {
            userSkill.setSkill(skill.get());
        } else {
            throw new RuntimeException("Skill not found with id: " + request.getSkillId());
        }
        
        userSkill.setProficiencyLevel(request.getProficiencyLevel());
        userSkill.setYearsOfExperience(request.getYearsOfExperience());
        userSkill.setCreatedAt(LocalDateTime.now());
        userSkill.setLastAssessed(LocalDateTime.now());
        
        return userSkillRepository.save(userSkill);
    }

    @Override
    public Optional<UserSkill> findUserSkillById(String id) {
        return userSkillRepository.findById(id);
    }

    @Override
    public List<UserSkill> findUserSkillsByUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return userSkillRepository.findByUser(user.get());
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    @Override
    public List<UserSkill> findUserSkillsBySkill(String skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isPresent()) {
            return userSkillRepository.findBySkill(skill.get());
        }
        throw new RuntimeException("Skill not found with id: " + skillId);
    }

    @Override
    public List<UserSkill> findUserSkillsByProficiencyLevel(SkillLevel proficiencyLevel) {
        return userSkillRepository.findByProficiencyLevel(proficiencyLevel);
    }

    @Override
    public UserSkill updateUserSkill(String id, UserSkillRequest request) {
        Optional<UserSkill> existingUserSkill = userSkillRepository.findById(id);
        if (existingUserSkill.isPresent()) {
            UserSkill userSkill = existingUserSkill.get();
            
            if (request.getUserId() != null) {
                Optional<User> user = userRepository.findById(request.getUserId());
                if (user.isPresent()) {
                    userSkill.setUser(user.get());
                } else {
                    throw new RuntimeException("User not found with id: " + request.getUserId());
                }
            }
            
            if (request.getSkillId() != null) {
                Optional<Skill> skill = skillRepository.findById(request.getSkillId());
                if (skill.isPresent()) {
                    userSkill.setSkill(skill.get());
                } else {
                    throw new RuntimeException("Skill not found with id: " + request.getSkillId());
                }
            }
            
            userSkill.setProficiencyLevel(request.getProficiencyLevel());
            userSkill.setYearsOfExperience(request.getYearsOfExperience());
            userSkill.setLastAssessed(LocalDateTime.now());
            
            return userSkillRepository.save(userSkill);
        }
        throw new RuntimeException("User skill not found with id: " + id);
    }

    @Override
    public void removeUserSkill(String id) {
        userSkillRepository.deleteById(id);
    }

    @Override
    public List<UserSkill> findSkillGapsForUser(String userId) {

        return findUserSkillsByUser(userId).stream()
                .filter(userSkill -> userSkill.getProficiencyLevel().ordinal() < 
                        userSkill.getSkill().getRequiredLevel().ordinal())
                .toList();
    }
}