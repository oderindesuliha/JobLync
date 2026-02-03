package org.peejay.joblync.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.Status;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.JobApplicationRepository;
import org.peejay.joblync.data.repository.JobPostingRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.JobApplicationRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobApplicationServiceImplStatusTransitionTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobApplicationServiceImpl jobApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateJobApplicationStatus_ShouldThrowException_WhenInvalidTransition() {
        // Arrange - Application is at APPLIED, trying to jump to HIRED
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.APPLIED);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            jobApplicationService.updateJobApplicationStatus("1", "HIRED")
        );
        
        assertTrue(exception.getMessage().contains("Invalid status transition"));
        assertTrue(exception.getMessage().contains("APPLIED") && exception.getMessage().contains("HIRED"));
        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldAllow_AppliedToScreening() {
        // Arrange
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.APPLIED);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "SCREENING");

        // Assert
        assertNotNull(result);
        assertEquals(Status.SCREENING, result.getStatus());
        verify(jobApplicationRepository, times(1)).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldAllow_ScreeningToInterview() {
        // Arrange
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.SCREENING);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "INTERVIEW");

        // Assert
        assertNotNull(result);
        assertEquals(Status.INTERVIEW, result.getStatus());
        verify(jobApplicationRepository, times(1)).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldAllow_InterviewToOffer() {
        // Arrange
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.INTERVIEW);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "OFFER");

        // Assert
        assertNotNull(result);
        assertEquals(Status.OFFER, result.getStatus());
        verify(jobApplicationRepository, times(1)).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldAllow_OfferToHired() {
        // Arrange
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.OFFER);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "HIRED");

        // Assert
        assertNotNull(result);
        assertEquals(Status.HIRED, result.getStatus());
        verify(jobApplicationRepository, times(1)).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldAllow_RejectAtAnyStage() {
        // Arrange - Can reject from any stage
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.INTERVIEW);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "REJECTED");

        // Assert
        assertNotNull(result);
        assertEquals(Status.REJECTED, result.getStatus());
        verify(jobApplicationRepository, times(1)).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldNotAllow_AnyTransitionFromRejected() {
        // Arrange - REJECTED is terminal
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.REJECTED);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            jobApplicationService.updateJobApplicationStatus("1", "HIRED")
        );
        
        assertTrue(exception.getMessage().contains("Invalid status transition"));
        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldNotAllow_AnyTransitionFromHired() {
        // Arrange - HIRED is terminal
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.HIRED);
        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            jobApplicationService.updateJobApplicationStatus("1", "REJECTED")
        );
        
        assertTrue(exception.getMessage().contains("Invalid status transition"));
        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void applyForJob_ShouldThrowException_WhenDuplicateApplication() {
        // Arrange
        JobApplicationRequest request = new JobApplicationRequest();
        request.setApplicantId("1");
        request.setJobPostingId("1");

        User applicant = new User();
        applicant.setFirstName("John");
        applicant.setLastName("Doe");
        applicant.setEmail("john.doe@example.com");

        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("Software Engineer");

        when(userRepository.findById("1")).thenReturn(Optional.of(applicant));
        when(jobPostingRepository.findById("1")).thenReturn(Optional.of(jobPosting));
        when(jobApplicationRepository.existsByApplicantAndJob(applicant, jobPosting)).thenReturn(true);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            jobApplicationService.applyForJob(request)
        );
        
        assertEquals("User has already applied for this job", exception.getMessage());
        verify(jobApplicationRepository, never()).save(any());
    }
}
