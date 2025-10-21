package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.services.EmailService;
import org.peejay.joblync.services.UserService;
import org.peejay.joblync.utils.JwtUtil;
import org.peejay.joblync.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordGenerator passwordGenerator, EmailService emailService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new RuntimeException("User with email " + userRegisterRequest.getEmail() + " already exists");
        }
        
        // Validate password and confirm password match
        if (userRegisterRequest.getPassword() != null && 
            !userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password do not match");
        }

        // Generate temporary password if not provided
        String passwordToUse;
        if (userRegisterRequest.getPassword() != null && !userRegisterRequest.getPassword().isEmpty()) {
            passwordToUse = userRegisterRequest.getPassword();
        } else {
            passwordToUse = passwordGenerator.generatePassword();
        }
        
        String encodedPassword = passwordEncoder.encode(passwordToUse);

        // Create new user
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setRole(userRegisterRequest.getRole() != null ? userRegisterRequest.getRole() : Role.APPLICANT);
        user.setPassword(encodedPassword);
        user.setHireDate(LocalDateTime.now());

        // Save user
        User savedUser = userRepository.save(user);

        // Send registration email with password (only if we generated it)
        if (userRegisterRequest.getPassword() == null || userRegisterRequest.getPassword().isEmpty()) {
            try {
                emailService.sendRegistrationEmail(user.getEmail(), user.getFirstName(), passwordToUse);
            } catch (Exception e) {
                // Log the error but don't fail the registration
                System.err.println("Failed to send registration email: " + e.getMessage());
            }
        }

        // Return response
        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setMessage("User registered successfully.");

        return response;
    }

    @Override
    public JwtResponse loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginRequest.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with email: " + userLoginRequest.getEmail());
        }
        
        User user = userOptional.get();
        
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        
        JwtResponse response = new JwtResponse();
        response.setJwtToken(jwtToken);
        response.setEmail(user.getEmail());
        response.setRoles(List.of(user.getRole().name()));
        
        return response;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            // Update only the provided fields
            if (user.getFirstName() != null) updatedUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) updatedUser.setLastName(user.getLastName());
            if (user.getEmail() != null) updatedUser.setEmail(user.getEmail());
            if (user.getPhoneNumber() != null) updatedUser.setPhoneNumber(user.getPhoneNumber());
            if (user.getDepartment() != null) updatedUser.setDepartment(user.getDepartment());
            if (user.getPosition() != null) updatedUser.setPosition(user.getPosition());
            if (user.getEmployeeId() != null) updatedUser.setEmployeeId(user.getEmployeeId());
            if (user.getDateOfBirth() != null) updatedUser.setDateOfBirth(user.getDateOfBirth());
            if (user.getAddress() != null) updatedUser.setAddress(user.getAddress());
            if (user.getEmergencyContactName() != null) updatedUser.setEmergencyContactName(user.getEmergencyContactName());
            if (user.getEmergencyContactPhone() != null) updatedUser.setEmergencyContactPhone(user.getEmergencyContactPhone());
            if (user.getBankAccountNumber() != null) updatedUser.setBankAccountNumber(user.getBankAccountNumber());
            if (user.getBankName() != null) updatedUser.setBankName(user.getBankName());
            if (user.getSalary() != null) updatedUser.setSalary(user.getSalary());
            if (user.getJobLevel() != null) updatedUser.setJobLevel(user.getJobLevel());
            if (user.getManagerId() != null) updatedUser.setManagerId(user.getManagerId());
            
            updatedUser.setLastPromotionDate(user.getLastPromotionDate());
            updatedUser.setTerminationDate(user.getTerminationDate());
            updatedUser.setEmploymentStatus(user.getEmploymentStatus());
            
            return userRepository.save(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public void resetUserPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Generate new temporary password
            String temporaryPassword = passwordGenerator.generatePassword();
            String encodedPassword = passwordEncoder.encode(temporaryPassword);
            
            // Update user's password
            user.setPassword(encodedPassword);
            userRepository.save(user);
            
            // Send email with new temporary password
            try {
                emailService.sendPasswordEmail(user.getEmail(), temporaryPassword);
            } catch (Exception e) {
                // Log the error but don't fail the operation
                System.err.println("Failed to send password reset email: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
    
    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
    
    @Override
    public List<User> findByDepartment(String department) {
        return userRepository.findByDepartment(department);
    }
    
    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    @Override
    public List<User> findSubordinates(String managerId) {
        return userRepository.findByManagerId(managerId);
    }
    
    @Override
    public User changeUserRole(String userId, Role newRole) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(newRole);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
    
    @Override
    public User updateEmployeeInfo(String userId, User employeeInfo) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Update employee-specific information
            if (employeeInfo.getDepartment() != null) user.setDepartment(employeeInfo.getDepartment());
            if (employeeInfo.getPosition() != null) user.setPosition(employeeInfo.getPosition());
            if (employeeInfo.getEmployeeId() != null) user.setEmployeeId(employeeInfo.getEmployeeId());
            if (employeeInfo.getDateOfBirth() != null) user.setDateOfBirth(employeeInfo.getDateOfBirth());
            if (employeeInfo.getAddress() != null) user.setAddress(employeeInfo.getAddress());
            if (employeeInfo.getEmergencyContactName() != null) user.setEmergencyContactName(employeeInfo.getEmergencyContactName());
            if (employeeInfo.getEmergencyContactPhone() != null) user.setEmergencyContactPhone(employeeInfo.getEmergencyContactPhone());
            if (employeeInfo.getBankAccountNumber() != null) user.setBankAccountNumber(employeeInfo.getBankAccountNumber());
            if (employeeInfo.getBankName() != null) user.setBankName(employeeInfo.getBankName());
            if (employeeInfo.getSalary() != null) user.setSalary(employeeInfo.getSalary());
            if (employeeInfo.getJobLevel() != null) user.setJobLevel(employeeInfo.getJobLevel());
            if (employeeInfo.getManagerId() != null) user.setManagerId(employeeInfo.getManagerId());
            if (employeeInfo.getHireDate() != null) user.setHireDate(employeeInfo.getHireDate());
            if (employeeInfo.getTerminationDate() != null) user.setTerminationDate(employeeInfo.getTerminationDate());
            if (employeeInfo.getEmploymentStatus() != null) user.setEmploymentStatus(employeeInfo.getEmploymentStatus());
            
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
}