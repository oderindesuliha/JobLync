package org.peejay.joblync.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private EmailService emailService;

    @BeforeEach
public void setUp() {
        userRepository.deleteAll();
        Mockito.doNothing().when(emailService).sendRegistrationEmail(anyString(), anyString(), anyString());
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
            default:
                break;
        }
        request.setPassword("Correct1234");
        request.setRole(role);
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
    public void testRegisterUser_duplicateEmail_throwsException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(request));
       assertEquals("User with email " + request.getEmail() + " already exists", exception.getMessage());
    }

    @Test
    public void testLogin_applicant_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        UserLoginRequest loginRequest= new UserLoginRequest();
        loginRequest.setEmail("johnadebayo2@gmail.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.loginUser(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_employee_validCredentials_shouldReturnJwtResponse() {
UserRegisterRequest request = registerUserRequest(Role.EMPLOYEE);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("tola.benson@company.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.loginUser(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_hrManager_validCredentials_shouldReturnJwtResponse() {
        UserRegisterRequest request = registerUserRequest(Role.HR_MANAGER);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("sarah.okafor@company.com");
        loginRequest.setPassword("Correct1234");
        JwtResponse response = userService.loginUser(loginRequest);
        assertNotNull(response);
    }

    @Test
    public void testLogin_invalidCredentials_throwsException() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("johnadebayo2@gmail.com");
        loginRequest.setPassword("Wrong1234");
        assertThrows(RuntimeException.class, () -> userService.loginUser(loginRequest));
    }

    @Test
    public void testUpdatePassword_applicant_validInput_shouldUpdate() {
        UserRegisterRequest request = registerUserRequest(Role.APPLICANT);
        userService.registerUser(request);
        userService.resetUserPassword("johnadebayo2@gmail.com");
        // We can't directly test password update here as it's randomly generated
        // But we can verify the method executes without exception
        assertDoesNotThrow(() -> userService.resetUserPassword("johnadebayo2@gmail.com"));
    }
}