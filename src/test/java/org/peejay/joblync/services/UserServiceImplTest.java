package org.peejay.joblync.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.ApplicantRepository;
import org.peejay.joblync.data.repositories.EmployeeRepository;
import org.peejay.joblync.data.repositories.HRManagerRepository;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.requests.SubAdminRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.InvalidRoleException;
import org.peejay.joblync.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HRManagerRepository hrManagerRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        applicantRepository.deleteAll();
        employeeRepository.deleteAll();
        hrManagerRepository.deleteAll();
        Mockito.doNothing().when(emailService).sendRegistrationEmail(anyString(), anyString(), anyString());
        Mockito.doNothing().when(emailService).sendPasswordResetEmail(anyString(), anyString(), anyString());
        Mockito.doNothing().when(emailService).sendPasswordEmail(anyString(), anyString());
    }

    private UserRegisterRequest registerUserRequest(Role role) {
        UserRegisterRequest request = new UserRegisterRequest();
        switch (role) {
            case APPLICANT:
                request.setFirstName("John");
                request.setLastName("Adebayo");
                request.setEmail("johnadebayo2@gmail.com");
                request.setPhoneNumber("+2348031234567");
                break;
            case HR_MANAGER:
                request.setFirstName("Sarah");
                request.setLastName("Okafor");
                request.setEmail("sarah.okafor@company.com");
                request.setPhoneNumber("+2348059876543");
                break;
            case RECRUITER:
                request.setFirstName("Chidi");
                request.setLastName("Eze");
                request.setEmail("chidieze@recruitment.ng");
                request.setPhoneNumber("+2348094567890");
                break;
            case EMPLOYEE:
                request.setFirstName("Tola");
                request.setLastName("Benson");
                request.setEmail("tola.benson@company.com");
                request.setPhoneNumber("+2348022223333");
                break;
            case ADMIN:
                request.setFirstName("Admin");
                request.setLastName("User");
                request.setEmail("admin@company.com");
                request.setPhoneNumber("+2348011112222");
                break;
            case SUB_ADMIN:
                request.setFirstName("Sub");
                request.setLastName("Admin");
                request.setEmail("subadmin@company.com");
                request.setPhoneNumber("+2348012345678");
                break;
            default:
                throw new InvalidRoleException("Invalid role");
        }
        request.setPassword("Correct1234");
        request.setRole(role);
        return request;
    }

    private SubAdminRequest registerSubAdminRequest() {
        SubAdminRequest request = new SubAdminRequest();
        request.setFullName("Sub Admin");
        request.setEmail("subadmin@company.com");
        return request;
    }

    @Test
    public void testRegisterUser_applicant_validRequest_shouldSave() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals("johnadebayo2@gmail.com", response.getEmail());
        assertEquals(Role.APPLICANT, response.getRole());
        assertEquals(1, userRepository.count());
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals("John", savedUser.get().getFirstName());
    }

    @Test
    public void testRegisterUser_employee_validRequest_shouldSave() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals("tola.benson@company.com", response.getEmail());
        assertEquals(Role.EMPLOYEE, response.getRole());
        assertEquals(1, userRepository.count());
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals("Tola", savedUser.get().getFirstName());
    }

    @Test
    public void testRegisterUser_hrManager_validRequest_shouldSave() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals("sarah.okafor@company.com", response.getEmail());
        assertEquals(Role.HR_MANAGER, response.getRole());
        assertEquals(1, userRepository.count());
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals("Sarah", savedUser.get().getFirstName());
    }

    @Test
    public void testRegisterUser_recruiter_validRequest_shouldSave() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals("chidieze@recruitment.ng", response.getEmail());
        assertEquals(Role.RECRUITER, response.getRole());
        assertEquals(1, userRepository.count());
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals("Chidi", savedUser.get().getFirstName());
    }

    @Test
    public void testRegisterUser_duplicateEmail_throwsUserException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        assertThrows(UserException.class, () -> userService.registerUser(request), "Email already exists");
    }

    @Test
    public void testRegisterSubAdmin_invalidEmail_throwsUserException() {
        SubAdminRequest request = new SubAdminRequest();
        request.setFullName("Sub Admin");
        request.setEmail("invalid-email");
        assertThrows(UserException.class, () -> userService.registerSubAdmin(request), "Invalid email format");
    }

    @Test
    public void testRegisterSubAdmin_emptyFullName_throwsUserException() {
        SubAdminRequest request = new SubAdminRequest();
        request.setFullName("");
        request.setEmail("subadmin@company.com");
        assertThrows(UserException.class, () -> userService.registerSubAdmin(request), "Sub-admin registration failed: full name and email are required");
    }


    @Test
    public void testRegisterSubAdmin_longFullName_throwsUserException() {
        SubAdminRequest request = new SubAdminRequest();
        request.setFullName("A".repeat(101));
        request.setEmail("subadmin@company.com");
        assertThrows(UserException.class, () -> userService.registerSubAdmin(request), "Full name must be between 2 and 100 characters");
    }

    @Test
    public void testLogin_applicant_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("johnadebayo2@gmail.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.login(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_employee_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("tola.benson@company.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.login(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_hrManager_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("sarah.okafor@company.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.login(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_recruiter_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("chidieze@recruitment.ng");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.login(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_invalidCredentials_throwsUserException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("johnadebayo2@gmail.com");
        loginRequest.setPassword("Wrong1234");
        assertThrows(UserException.class, () -> userService.login(loginRequest), "Invalid email or password");
    }

    @Test
    public void testFindUserByEmail_applicant_validEmail_shouldReturnUser() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        UserRegisterResponse response = userService.findUserByEmail("johnadebayo2@gmail.com");
        assertNotNull(response);
        assertEquals("johnadebayo2@gmail.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertEquals(Role.APPLICANT, response.getRole());
    }

    @Test
    public void testFindUserByEmail_employee_validEmail_shouldReturnUser() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        UserRegisterResponse response = userService.findUserByEmail("tola.benson@company.com");
        assertNotNull(response);
        assertEquals("tola.benson@company.com", response.getEmail());
        assertEquals("Tola", response.getFirstName());
        assertEquals(Role.EMPLOYEE, response.getRole());
    }

    @Test
    public void testFindUserByEmail_hrManager_validEmail_shouldReturnUser() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        UserRegisterResponse response = userService.findUserByEmail("sarah.okafor@company.com");
        assertNotNull(response);
        assertEquals("sarah.okafor@company.com", response.getEmail());
        assertEquals("Sarah", response.getFirstName());
        assertEquals(Role.HR_MANAGER, response.getRole());
    }

    @Test
    public void testFindUserByEmail_recruiter_validEmail_shouldReturnUser() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        UserRegisterResponse response = userService.findUserByEmail("chidieze@recruitment.ng");
        assertNotNull(response);
        assertEquals("chidieze@recruitment.ng", response.getEmail());
        assertEquals("Chidi", response.getFirstName());
        assertEquals(Role.RECRUITER, response.getRole());
    }

    @Test
    public void testFindUserByEmail_invalidEmail_throwsUserException() {
        assertThrows(UserException.class, () -> userService.findUserByEmail("invalid@company.com"), "User not found");
    }

    @Test
    public void testUpdatePassword_applicant_validInput_shouldUpdate() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.updatePassword("johnadebayo2@gmail.com", "NewPass1234");
        Optional<User> updatedUser = userRepository.findByEmail("johnadebayo2@gmail.com");
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches("NewPass1234", updatedUser.get().getPassword()));
    }

    @Test
    public void testUpdatePassword_employee_validInput_shouldUpdate() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        userService.updatePassword("tola.benson@company.com", "NewPass1234");
        Optional<User> updatedUser = userRepository.findByEmail("tola.benson@company.com");
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches("NewPass1234", updatedUser.get().getPassword()));
    }

    @Test
    public void testUpdatePassword_hrManager_validInput_shouldUpdate() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        userService.updatePassword("sarah.okafor@company.com", "NewPass1234");
        Optional<User> updatedUser = userRepository.findByEmail("sarah.okafor@company.com");
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches("NewPass1234", updatedUser.get().getPassword()));
    }

    @Test
    public void testUpdatePassword_recruiter_validInput_shouldUpdate() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        userService.updatePassword("chidieze@recruitment.ng", "NewPass1234");
        Optional<User> updatedUser = userRepository.findByEmail("chidieze@recruitment.ng");
        assertTrue(updatedUser.isPresent());
        assertTrue(passwordEncoder.matches("NewPass1234", updatedUser.get().getPassword()));
    }


    @Test
    public void testUpdatePassword_invalidPassword_throwsUserException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        assertThrows(UserException.class, () -> userService.updatePassword("johnadebayo2@gmail.com", "weak"),
                "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
    }

    @Test
    public void testDisableUser_applicant_activeUser_shouldDisable() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.disableUser("johnadebayo2@gmail.com");
        Optional<User> user = userRepository.findByEmail("johnadebayo2@gmail.com");
        assertTrue(user.isPresent());
        assertFalse(user.get().isActive());
    }

    @Test
    public void testDisableUser_employee_activeUser_shouldDisable() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        userService.disableUser("tola.benson@company.com");
        Optional<User> user = userRepository.findByEmail("tola.benson@company.com");
        assertTrue(user.isPresent());
        assertFalse(user.get().isActive());
    }

    @Test
    public void testDisableUser_hrManager_activeUser_shouldDisable() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        userService.disableUser("sarah.okafor@company.com");
        Optional<User> user = userRepository.findByEmail("sarah.okafor@company.com");
        assertTrue(user.isPresent());
        assertFalse(user.get().isActive());
    }

    @Test
    public void testDisableUser_recruiter_activeUser_shouldDisable() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        userService.disableUser("chidieze@recruitment.ng");
        Optional<User> user = userRepository.findByEmail("chidieze@recruitment.ng");
        assertTrue(user.isPresent());
        assertFalse(user.get().isActive());
    }

    @Test
    public void testDisableUser_alreadyDisabled_throwsUserException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.disableUser("johnadebayo2@gmail.com");
        assertThrows(UserException.class, () -> userService.disableUser("johnadebayo2@gmail.com"), "User is already disabled");
    }

    @Test
    public void testEnableUser_applicant_disabledUser_shouldEnable() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.disableUser("johnadebayo2@gmail.com");
        userService.enableUser("johnadebayo2@gmail.com");
        Optional<User> user = userRepository.findByEmail("johnadebayo2@gmail.com");
        assertTrue(user.isPresent());
        assertTrue(user.get().isActive());
    }

    @Test
    public void testEnableUser_employee_disabledUser_shouldEnable() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        userService.disableUser("tola.benson@company.com");
        userService.enableUser("tola.benson@company.com");
        Optional<User> user = userRepository.findByEmail("tola.benson@company.com");
        assertTrue(user.isPresent());
        assertTrue(user.get().isActive());
    }

    @Test
    public void testEnableUser_hrManager_disabledUser_shouldEnable() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        userService.disableUser("sarah.okafor@company.com");
        userService.enableUser("sarah.okafor@company.com");
        Optional<User> user = userRepository.findByEmail("sarah.okafor@company.com");
        assertTrue(user.isPresent());
        assertTrue(user.get().isActive());
    }

    @Test
    public void testEnableUser_recruiter_disabledUser_shouldEnable() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        userService.disableUser("chidieze@recruitment.ng");
        userService.enableUser("chidieze@recruitment.ng");
        Optional<User> user = userRepository.findByEmail("chidieze@recruitment.ng");
        assertTrue(user.isPresent());
        assertTrue(user.get().isActive());
    }

    @Test
    public void testEnableUser_alreadyEnabled_throwsUserException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        assertThrows(UserException.class, () -> userService.enableUser("johnadebayo2@gmail.com"), "User is already enabled");
    }

    @Test
    public void testDeleteUser_applicant_validEmail_shouldDelete() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.deleteUser("johnadebayo2@gmail.com");
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testDeleteUser_employee_validEmail_shouldDelete() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        userService.deleteUser("tola.benson@company.com");
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testDeleteUser_hrManager_validEmail_shouldDelete() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        userService.deleteUser("sarah.okafor@company.com");
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testDeleteUser_recruiter_validEmail_shouldDelete() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        userService.deleteUser("chidieze@recruitment.ng");
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testDeleteUser_invalidEmail_throwsUserException() {
        assertThrows(UserException.class, () -> userService.deleteUser("invalid@company.com"), "User not found");
    }

    @Test
    public void testGetAllUsers_multipleUsers_shouldReturnList() {
        userService.registerUser(registerUserRequest(Role.APPLICANT));
        userService.registerUser(registerUserRequest(Role.EMPLOYEE));
        userService.registerUser(registerUserRequest(Role.HR_MANAGER));
        userService.registerUser(registerUserRequest(Role.RECRUITER));
        List<User> users = userService.getAllUsers();
        assertEquals(4, users.size());
    }

    @Test
    public void testLogout_applicant_validEmail_shouldPass() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        assertDoesNotThrow(() -> userService.logout("johnadebayo2@gmail.com"));
    }

    @Test
    public void testLogout_employee_validEmail_shouldPass() {
        UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        assertDoesNotThrow(() -> userService.logout("tola.benson@company.com"));
    }

    @Test
    public void testLogout_hrManager_validEmail_shouldPass() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        assertDoesNotThrow(() -> userService.logout("sarah.okafor@company.com"));
    }

    @Test
    public void testLogout_recruiter_validEmail_shouldPass() {
        UserRegisterRequest request = registerUserRequest(Role.RECRUITER);
        userService.registerUser(request);
        assertDoesNotThrow(() -> userService.logout("chidieze@recruitment.ng"));
    }

    @Test
    public void testLogout_invalidEmail_throwsUserException() {
        assertThrows(UserException.class, () -> userService.logout("invalid@company.com"), "User not found");
    }
}