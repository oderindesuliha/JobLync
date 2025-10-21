package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.InternalJobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternalJobPostingRepository extends JpaRepository<InternalJobPosting, String> {
    List<InternalJobPosting> findByDepartment(String department);
    List<InternalJobPosting> findByIsOpen(boolean isOpen);
    List<InternalJobPosting> findByCreatedBy(String createdBy);
    List<InternalJobPosting> findByExperienceLevel(ExperienceLevel experienceLevel);
}