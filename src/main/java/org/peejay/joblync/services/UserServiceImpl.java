package org.peejay.joblync.services;

import org.peejay.joblync.data.models.*;
import org.peejay.joblync.data.repositories.HRManagerRepository;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.*;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.UserException;
import org.peejay.joblync.security.JwtUtil;
import org.peejay.joblync.utils.UserMapper;
import org.peejay.joblync.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HRManagerRepository hrManagerRepository;

    @Autowired
    private PasswordEncoder password;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserValidations userValidation;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserRegisterResponse registerApplicant(ApplicantRegisterRequest request) {
        Applicant user = new Applicant();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists");
        }

        setCommonFields(user, request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhoneNumber(), request.getPassword(), request.getProfilePicture());
        user.setRole(Role.APPLICANT);
        user.setResumeUrl(request.getResumeUrl());
        user.setPortfolioUrl(request.getPortfolioUrl());
        user.setStatus(request.getStatus());
        return new UserRegisterResponse(userRepository.save(user));
    }

    @Override
    public UserRegisterResponse registerRecruiter(RecruiterRegisterRequest recruiterRequest) {
        Recruiter user = new Recruiter();
        setCommonFields(user, recruiterRequest.getFirstName(), recruiterRequest.getLastName(), recruiterRequest.getEmail(), recruiterRequest.getPhoneNumber(), recruiterRequest.getPassword(), recruiterRequest.getProfilePicture());
        user.setRole(Role.RECRUITER);
        user.setCompanyName(recruiterRequest.getCompanyName());
        user.setCompanyWebsite(recruiterRequest.getCompanyWebsite());
        return new UserRegisterResponse(userRepository.save(user));
    }

    @Override
    public UserRegisterResponse registerAdmin(AdminRegisterRequest adminRequest) {
        Admin user = new Admin();
        setCommonFields(user, adminRequest.getFirstName(), adminRequest.getLastName(), adminRequest.getEmail(), adminRequest.getPhoneNumber(), adminRequest.getPassword(), adminRequest.getProfilePicture());
        user.setRole(Role.ADMIN);
        user.setAdminLevel(adminRequest.getAdminLevel());
        return new UserRegisterResponse(userRepository.save(user));
    }

    @Override
    public UserRegisterResponse registerHRManager(HRManagerRegisterRequest hrManagerRequest) {
        HRManager user = new HRManager();
        setCommonFields(user, hrManagerRequest.getFirstName(), hrManagerRequest.getLastName(), hrManagerRequest.getEmail(), hrManagerRequest.getPhoneNumber(), hrManagerRequest.getPassword(), hrManagerRequest.getProfilePicture());
        user.setRole(Role.HR_MANAGER);
        user.setDepartment(hrManagerRequest.getDepartment());
        return new UserRegisterResponse(userRepository.save(user));
    }

    @Override
    public UserRegisterResponse registerEmployee(EmployeeRegisterRequest employeeRequest) {
        Employee user = new Employee();
        setCommonFields(user, employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getEmail(), employeeRequest.getPhoneNumber(), employeeRequest.getPassword(), employeeRequest.getProfilePicture());
        user.setRole(Role.EMPLOYEE);
        user.setJobTitle(employeeRequest.getJobTitle());
        user.setCompanyName(employeeRequest.getCompanyName());
        user.setStartDate(employeeRequest.getStartDate());

        HRManager manager = hrManagerRepository.findById(employeeRequest.getHrManagerId())
                .orElseThrow(() -> new RuntimeException("HR Manager not found"));
        user.setHrManager(manager);

        return new UserRegisterResponse(userRepository.save(user));
    }

    private void setCommonFields(User user, String firstName, String lastName, String email,
                                 String phone, String password, String profilePicture) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setProfilePicture(profilePicture);
        user.setActive(true);
        user.setDateJoined(LocalDateTime.now());
    }


    @Override
    public JwtResponse login(UserLoginRequest request) {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isEmpty()) {
            throw new UserException("Invalid email or password");
        }
        User user = savedUser.get();
        if (!user.isActive()) {
            throw new UserException("User is not active");
        }
        if (!password.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password");
        }
        List<String> roles = List.of(user.getRole().name());
        String jwtToken = jwtUtil.generateToken(user.getEmail(), roles);
        JwtResponse response = new JwtResponse();
        response.setJwtToken(jwtToken);
        response.setEmail(user.getEmail());
        response.setRoles(roles);
        return response;
    }

    @Override
    public UserRegisterResponse findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        return userMapper.mapToRegisterResponse(user.get());
    }

    @Override
    @Transactional
    public void updatePassword(String email) {
        userValidation.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
        User existingUser = user.get();
        existingUser.setPassword(password.encode((CharSequence) password));
        userRepository.save(existingUser);
        emailService.sendPasswordResetEmail(email, existingUser.getFirstName(), existingUser.getLastName());
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void logout(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }
    }

}