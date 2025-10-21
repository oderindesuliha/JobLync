package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.dtos.requests.CareerDevelopmentPlanRequest;
import org.peejay.joblync.services.CareerDevelopmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-development-plans")
public class CareerDevelopmentPlanController {

private final CareerDevelopmentPlanService careerDevelopmentPlanService;

    @Autowired
    public CareerDevelopmentPlanController(CareerDevelopmentPlanService careerDevelopmentPlanService) {
        this.careerDevelopmentPlanService = careerDevelopmentPlanService;
    }

    @PostMapping
    public ResponseEntity<CareerDevelopmentPlan> createCareerDevelopmentPlan(@RequestBody CareerDevelopmentPlanRequest request) {
        CareerDevelopmentPlan careerDevelopmentPlan = careerDevelopmentPlanService.createCareerDevelopmentPlan(request);
        return ResponseEntity.ok(careerDevelopmentPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDevelopmentPlan> getCareerDevelopmentPlanById(@PathVariable String id) {
        return careerDevelopmentPlanService.findCareerDevelopmentPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<CareerDevelopmentPlan>> getCareerDevelopmentPlansByEmployee(@PathVariable String employeeId) {
        List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByEmployee(employeeId);
        return ResponseEntity.ok(careerDevelopmentPlans);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<CareerDevelopmentPlan>>getCareerDevelopmentPlansByManager(@PathVariable String managerId) {
        List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByManager(managerId);
        return ResponseEntity.ok(careerDevelopmentPlans);
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<CareerDevelopmentPlan>> getCareerDevelopmentPlansByEmployeeAndStatus(
            @PathVariable String employeeId,
            @PathVariable PlanStatus status) {
        List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByEmployeeAndStatus(employeeId, status);
        return ResponseEntity.ok(careerDevelopmentPlans);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<CareerDevelopmentPlan>> getCareerDevelopmentPlansByDepartment(@PathVariable String department) {
        List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByDepartment(department);
        return ResponseEntity.ok(careerDevelopmentPlans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerDevelopmentPlan> updateCareerDevelopmentPlan(
            @PathVariable String id,
            @RequestBody CareerDevelopmentPlanRequest request) {
        CareerDevelopmentPlan updatedCareerDevelopmentPlan = careerDevelopmentPlanService.updateCareerDevelopmentPlan(id, request);
        return ResponseEntity.ok(updatedCareerDevelopmentPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareerDevelopmentPlan(@PathVariable String id) {
        careerDevelopmentPlanService.deleteCareerDevelopmentPlan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> completeCareerDevelopmentPlan(@PathVariable String id) {
        careerDevelopmentPlanService.completeCareerDevelopmentPlan(id);
        return ResponseEntity.noContent().build();
    }
}