package org.peejay.joblync.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.repository.JobPostingRepository;
import org.peejay.joblync.dtos.requests.JobPostingRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobPostingServiceImplTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingServiceImpl jobPostingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createJobPosting_ShouldCreateJobPosting_WhenValidRequest() {
        // Arrange
        JobPostingRequest request = new JobPostingRequest();
        request.setTitle("Software Engineer");
        request.setDescription("Develop amazing software");
        request.setRequirements("Java, Spring Boot");
        request.setCreatedBy("1");

        JobPosting jobPosting = new JobPosting();
        jobPosting.setId("1");
        jobPosting.setTitle("Software Engineer");
        jobPosting.setDescription("Develop amazing software");
        jobPosting.setRequirements("Java, Spring Boot");
        jobPosting.setCreatedBy("1");
        jobPosting.setCreatedAt(LocalDateTime.now());
        jobPosting.setUpdatedAt(LocalDateTime.now());

        when(jobPostingRepository.save(any(JobPosting.class))).thenReturn(jobPosting);

        // Act
        JobPosting result = jobPostingService.createJobPosting(request);

        // Assert
        assertNotNull(result);
        assertEquals("Software Engineer", result.getTitle());
        assertEquals("Develop amazing software", result.getDescription());
        assertEquals("Java, Spring Boot", result.getRequirements());
        assertEquals("1", result.getCreatedBy());

        verify(jobPostingRepository, times(1)).save(any(JobPosting.class));
    }

    @Test
    void findAllJobPostings_ShouldReturnAllJobPostings() {
        // Arrange
        List<JobPosting> jobPostings = new ArrayList<>();
        JobPosting job1 = new JobPosting();
        job1.setId("1");
        job1.setTitle("Software Engineer");
        jobPostings.add(job1);

        JobPosting job2 = new JobPosting();
        job2.setId("2");
        job2.setTitle("Product Manager");
        jobPostings.add(job2);

        when(jobPostingRepository.findAll()).thenReturn(jobPostings);

        // Act
        List<JobPosting> result = jobPostingService.findAllJobPostings();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Software Engineer", result.get(0).getTitle());
        assertEquals("Product Manager", result.get(1).getTitle());

        verify(jobPostingRepository, times(1)).findAll();
    }

    @Test
    void findJobPostingById_ShouldReturnJobPosting_WhenExists() {
        // Arrange
        JobPosting jobPosting = new JobPosting();
        jobPosting.setId("1");
        jobPosting.setTitle("Software Engineer");

        when(jobPostingRepository.findById("1")).thenReturn(Optional.of(jobPosting));

        // Act
        Optional<JobPosting> result = jobPostingService.findJobPostingById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Software Engineer", result.get().getTitle());
        verify(jobPostingRepository, times(1)).findById("1");
    }

    @Test
    void findJobPostingById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(jobPostingRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        Optional<JobPosting> result = jobPostingService.findJobPostingById("999");

        // Assert
        assertFalse(result.isPresent());
        verify(jobPostingRepository, times(1)).findById("999");
    }
}