package org.peejay.joblync.services;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.data.models.SkillLevel;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.models.UserSkill;
import org.peejay.joblync.dtos.requests.UserSkillRequest;

import java.util.List;
import java.util.Optional;

public interface UserSkillService {
    UserSkill addUserSkill(UserSkillRequest request);
    Optional<UserSkill> findUserSkillById(String id);
    List<UserSkill> findUserSkillsByUser(String userId);
    List<UserSkill> findUserSkillsBySkill(String skillId);
    List<UserSkill> findUserSkillsByProficiencyLevel(SkillLevel proficiencyLevel);
    UserSkill updateUserSkill(String id, UserSkillRequest request);
    void removeUserSkill(String id);
    List<UserSkill> findSkillGapsForUser(String userId);
}