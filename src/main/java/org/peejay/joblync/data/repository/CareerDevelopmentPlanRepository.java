package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerDevelopmentPlanRepository extends JpaRepository<CareerDevelopmentPlan, String> {
    List<CareerDevelopmentPlan> findByEmployee(User employee);
    List<CareerDevelopmentPlan> findByManager(User manager);
    List<CareerDevelopmentPlan> findByEmployeeAndStatus(User employee, PlanStatus status);

    List<CareerDevelopmentPlan> findByDepartment(String department);
}