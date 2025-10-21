package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.SuccessionPlan;
import org.peejay.joblync.data.models.SuccessionStatus;
import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuccessionPlanRepository extends JpaRepository<SuccessionPlan, String> {
    List<SuccessionPlan> findByIncumbent(User incumbent);
    List<SuccessionPlan> findBySuccessor(User successor);
    List<SuccessionPlan> findByCriticalPosition(JobPosting criticalPosition);
    List<SuccessionPlan> findByStatus(SuccessionStatus status);
    List<SuccessionPlan> findByDepartment(String department);
    List<SuccessionPlan> findByReadinessLevelGreaterThanEqual(Integer readinessLevel);
}