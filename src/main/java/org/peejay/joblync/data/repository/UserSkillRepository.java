package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.data.models.SkillLevel;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.models.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, String> {
    List<UserSkill> findByUser(User user);
    List<UserSkill> findBySkill(Skill skill);
    List<UserSkill> findByUserAndSkill(User user, Skill skill);
    List<UserSkill> findByProficiencyLevel(SkillLevel proficiencyLevel);
    Optional<UserSkill> findByUserIdAndSkillId(String userId, String skillId);
}