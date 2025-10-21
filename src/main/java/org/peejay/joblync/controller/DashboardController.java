package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        try {
            List<User> allUsers = userService.findAllUsers();
            
            Map<String, Object> stats = new HashMap<>();
            
            // Total employees
            stats.put("totalEmployees", allUsers.size());
            
            // Count by role
            long adminCount = allUsers.stream().filter(u -> u.getRole() == Role.ADMIN).count();
            long hrManagerCount = allUsers.stream().filter(u -> u.getRole() == Role.HR_MANAGER).count();
            long employeeCount = allUsers.stream().filter(u -> u.getRole() == Role.EMPLOYEE).count();
            long applicantCount = allUsers.stream().filter(u -> u.getRole() == Role.APPLICANT).count();
            
            stats.put("admins", adminCount);
            stats.put("hrManagers", hrManagerCount);
            stats.put("employees", employeeCount);
            stats.put("applicants", applicantCount);
            
            // Count by department (simplified)
            Map<String, Long> departmentCount = new HashMap<>();
            allUsers.stream()
                    .filter(u -> u.getDepartment() != null)
                    .forEach(u -> departmentCount.merge(u.getDepartment(), 1L, Long::sum));
            
            stats.put("departments", departmentCount);
            
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(true, "Dashboard stats retrieved successfully", stats);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorStats = new HashMap<>();
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(false, e.getMessage(), errorStats);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/recent")
    public ResponseEntity<ApiResponse<List<User>>> getRecentEmployees(@RequestParam(defaultValue = "5") int limit) {
        try {
            List<User> allUsers = userService.findAllUsers();
            // In a real implementation, you would sort by hire date
            // For now, we'll just take the first N users
            List<User> recentEmployees = allUsers.stream().limit(limit).toList();
            
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Recent employees retrieved successfully", recentEmployees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}