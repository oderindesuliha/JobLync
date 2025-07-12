package org.peejay.joblync.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.exceptions.InvalidRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    private UserRegisterRequest createUserRequest(Role role) {
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

            default:
                throw new InvalidRoleException("Invalid role");
        }
        request.setPassword("Correct1234");
        request.setRole(role);
        return request;
    }

    @Test
    public void testRegisterApplicant_validRequest_shouldSaveUser() {
        UserRegisterRequest request = createUserRequest(Role.APPLICANT);
        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals("johnadebayo2@gmail.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertEquals("Adebayo", response.getLastName());
        assertEquals(Role.APPLICANT, response.getRole());
        long count = userRepository.count();
        assertEquals(1, count);

        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        assertTrue(savedUser.isPresent());
        assertEquals("John", savedUser.get().getFirstName());
    }


}
