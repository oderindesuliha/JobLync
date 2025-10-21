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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobApplicationServiceImplTest {

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
    void applyForJob_ShouldCreateApplication_WhenValidRequest() {
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
        jobPosting.setDescription("Develop software applications");

        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplicant(applicant);
        jobApplication.setJob(jobPosting);
        jobApplication.setStatus(Status.PENDING);
        jobApplication.setAppliedAt(LocalDateTime.now());

        when(userRepository.findById("1")).thenReturn(Optional.of(applicant));
        when(jobPostingRepository.findById("1")).thenReturn(Optional.of(jobPosting));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(jobApplication);

        // Act
        JobApplication result = jobApplicationService.applyForJob(request);

        // Assert
        assertNotNull(result);
        assertEquals(Status.PENDING, result.getStatus());
        assertEquals(applicant, result.getApplicant());
        assertEquals(jobPosting, result.getJob());

        verify(userRepository, times(1)).findById("1");
        verify(jobPostingRepository, times(1)).findById("1");
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
    }

    @Test
    void applyForJob_ShouldThrowException_WhenApplicantNotFound() {
        // Arrange
        JobApplicationRequest request = new JobApplicationRequest();
        request.setApplicantId("999");
        request.setJobPostingId("1");

        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jobApplicationService.applyForJob(request);
        });

        assertEquals("Applicant not found with id: 999", exception.getMessage());
        verify(userRepository, times(1)).findById("999");
        verify(jobPostingRepository, never()).findById(any());
        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void updateJobApplicationStatus_ShouldUpdateStatus_WhenValidRequest() {
        // Arrange
        JobApplication existingApplication = new JobApplication();
        existingApplication.setStatus(Status.PENDING);

        when(jobApplicationRepository.findById("1")).thenReturn(Optional.of(existingApplication));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(existingApplication);

        // Act
        JobApplication result = jobApplicationService.updateJobApplicationStatus("1", "HIRED");

        // Assert
        assertNotNull(result);
        assertEquals(Status.HIRED, result.getStatus());

        verify(jobApplicationRepository, times(1)).findById("1");
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
    }

    @Test
    void updateJobApplicationStatus_ShouldThrowException_WhenApplicationNotFound() {
        // Arrange
        when(jobApplicationRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jobApplicationService.updateJobApplicationStatus("999", "HIRED");
        });

        assertEquals("Job application not found with id: 999", exception.getMessage());
        verify(jobApplicationRepository, times(1)).findById("999");
        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void findJobApplicationsByStatus_ShouldReturnApplications_WhenValidStatus() {
        // Arrange
        List<JobApplication> applications = new ArrayList<>();
        JobApplication app1 = new JobApplication();
        app1.setStatus(Status.PENDING);
        applications.add(app1);

        when(jobApplicationRepository.findByStatus(Status.PENDING)).thenReturn(applications);

        // Act
        List<JobApplication> result = jobApplicationService.findJobApplicationsByStatus("PENDING");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Status.PENDING, result.get(0).getStatus());

        verify(jobApplicationRepository, times(1)).findByStatus(Status.PENDING);
    }
}