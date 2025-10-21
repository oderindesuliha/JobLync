package org.peejay.joblync.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.peejay.joblync.data.models.EmploymentStatus;

import java.time.LocalDateTime;

@Data
public class EmployeeInfoRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phoneNumber;
    private String department;
    private String position;
    private String employeeId;
    private LocalDateTime dateOfBirth;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String bankAccountNumber;
    private String bankName;
    private LocalDateTime hireDate;
    private LocalDateTime terminationDate;
    private String managerId;
    private EmploymentStatus employmentStatus;
    private Double salary;
    private String jobLevel;
    
    // Explicit getters to resolve compilation issue
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public String getPosition() {
        return position;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getEmergencyContactName() {
        return emergencyContactName;
    }
    
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }
    
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public LocalDateTime getHireDate() {
        return hireDate;
    }
    
    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }
    
    public String getManagerId() {
        return managerId;
    }
    
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
    
    public Double getSalary() {
        return salary;
    }
    
    public String getJobLevel() {
        return jobLevel;
    }
    
}