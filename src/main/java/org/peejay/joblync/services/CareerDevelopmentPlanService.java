package org.peejay.joblync.services;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.dtos.requests.CareerDevelopmentPlanRequest;

import java.util.List;
import java.util.Optional;

public interface CareerDevelopmentPlanService {
    CareerDevelopmentPlan createCareerDevelopmentPlan(CareerDevelopmentPlanRequest request);
    Optional<CareerDevelopmentPlan> findCareerDevelopmentPlanById(String id);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployee(String employeeId);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByManager(String managerId);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployeeAndStatus(String employeeId, PlanStatus status);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByDepartment(String department);
    CareerDevelopmentPlan updateCareerDevelopmentPlan(String id, CareerDevelopmentPlanRequest request);
    void deleteCareerDevelopmentPlan(String id);
    void completeCareerDevelopmentPlan(String id);
}