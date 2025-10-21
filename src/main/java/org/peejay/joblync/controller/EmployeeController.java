package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.EmployeeInfoRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final UserService userService;

    @Autowired
    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllEmployees() {
        try {
            List<User> employees = userService.findAllUsers();
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Employees retrieved successfully", employees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getEmployeeById(@PathVariable String id) {
        try {
            return userService.findById(id)
                    .map(employee -> {
                        ApiResponse<User> response = new ApiResponse<>(true, "Employee retrieved successfully", employee);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<User> response = new ApiResponse<>(false, "Employee not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<User> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<User>>> getEmployeesByDepartment(@PathVariable String department) {
        try {
            List<User> employees = userService.findByDepartment(department);
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Employees retrieved successfully", employees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<User>>> getEmployeesByRole(@PathVariable Role role) {
        try {
            List<User> employees = userService.findByRole(role);
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Employees retrieved successfully", employees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<ApiResponse<List<User>>> getSubordinates(@PathVariable String managerId) {
        try {
            List<User> subordinates = userService.findSubordinates(managerId);
            ApiResponse<List<User>> response = new ApiResponse<>(true, "Subordinates retrieved successfully", subordinates);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<User>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateEmployee(@PathVariable String id, @RequestBody EmployeeInfoRequest employeeInfoRequest) {
        try {
            User employee = new User();
            employee.setFirstName(employeeInfoRequest.getFirstName());
            employee.setLastName(employeeInfoRequest.getLastName());
            employee.setEmail(employeeInfoRequest.getEmail());
            employee.setPhoneNumber(employeeInfoRequest.getPhoneNumber());
            employee.setDepartment(employeeInfoRequest.getDepartment());
            employee.setPosition(employeeInfoRequest.getPosition());
            employee.setEmployeeId(employeeInfoRequest.getEmployeeId());
            employee.setDateOfBirth(employeeInfoRequest.getDateOfBirth());
            employee.setAddress(employeeInfoRequest.getAddress());
            employee.setEmergencyContactName(employeeInfoRequest.getEmergencyContactName());
            employee.setEmergencyContactPhone(employeeInfoRequest.getEmergencyContactPhone());
            employee.setBankAccountNumber(employeeInfoRequest.getBankAccountNumber());
            employee.setBankName(employeeInfoRequest.getBankName());
            employee.setHireDate(employeeInfoRequest.getHireDate());
            employee.setTerminationDate(employeeInfoRequest.getTerminationDate());
            employee.setManagerId(employeeInfoRequest.getManagerId());
            employee.setEmploymentStatus(employeeInfoRequest.getEmploymentStatus());
            employee.setSalary(employeeInfoRequest.getSalary());
            employee.setJobLevel(employeeInfoRequest.getJobLevel());
            
            User updatedEmployee = userService.updateEmployeeInfo(id, employee);
            ApiResponse<User> response = new ApiResponse<>(true, "Employee updated successfully", updatedEmployee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<User> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<User>> changeEmployeeRole(@PathVariable String id, @RequestParam Role newRole) {
        try {
            User updatedEmployee = userService.changeUserRole(id, newRole);
            ApiResponse<User> response = new ApiResponse<>(true, "Employee role updated successfully", updatedEmployee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<User> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Employee deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}