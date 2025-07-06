package org.peejay.joblync.services;

import org.junit.jupiter.api.Test;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repositories.UserRepository;
import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceImplTest {
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserRegisterRequest registerApplicant(){
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("Alan");
        request.setLastName("Jola");
        request.setEmail("jola@gmail.com");
        request.setPhoneNumber("09090979450");
        request.setPassword("jj1234");
        request.setRole(Role.APPLICANT);
        return request;
    }

    @Test
    public void testToRegisterApplicant_shouldSaveUser() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("Ayo");
        request.setLastName("Biodun");
        request.setEmail("ab@gmail.com");
        request.setPhoneNumber("09090979450");
        request.setPassword("12345");
        request.setRole(Role.APPLICANT);

        UserRegisterResponse response = userService.registerUser(request);
        assertNotNull(response);
        assertEquals(1,userRepository.count());
    }

//    @Test
//    public void testToRegisterApplicant_loginApplicant_loginSuccessful() {
//        UserRegisterRequest request = registerApplicant();
//        userService.registerUser(request);
//
//        UserLoginRequest loginRequest = new UserLoginRequest();
//        loginRequest.setEmail(request.getEmail());
//        loginRequest.setPassword(request.getPassword());
//
//    }
}

