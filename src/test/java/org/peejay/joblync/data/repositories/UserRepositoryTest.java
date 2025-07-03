package org.peejay.joblync.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testThatSavesUser_AndFindsUserById() {
        User user = new User();
        user.setFirstName("Grace");
        user.setLastName("Adeoye");
        user.setEmail("grace.adeoye@gmail.com");
        user.setPhoneNumber("09090979450");
        user.setPassword("Password");
        user.setRole(Role.APPLICANT);
        user.setProfilePicture(null);
        user.setDateJoined(LocalDateTime.now());

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User saved = userRepository.save(user);
        Optional<User> result = userRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(Role.APPLICANT, result.get().getRole());
    }

    @Test
    public void testThanSavesUser_shouldFindUserByEmail() {
        User user = new User();
        user.setFirstName("Alan");
        user.setLastName("Jola");
        user.setEmail("alan@yahoo.com");
        user.setPassword("Alan123");
        user.setRole(Role.RECRUITER);
        user.setDateJoined(LocalDateTime.now());

        when(userRepository.findByEmail("alan@yahoo.com")).thenReturn(Optional.of(user));

        Optional<User> found = userRepository.findByEmail("alan@yahoo.com");

        assertTrue(found.isPresent());
        assertEquals("Alan", found.get().getFirstName());
        assertEquals(Role.RECRUITER, found.get().getRole());
    }
}
