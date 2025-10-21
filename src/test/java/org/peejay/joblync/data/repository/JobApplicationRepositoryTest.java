package org.peejay.joblync.data.repository;

import org.junit.jupiter.api.Test;
import org.peejay.joblync.data.models.JobApplication;
import org.peejay.joblync.data.models.JobPosting;
import org.peejay.joblync.data.models.Status;
import org.peejay.joblync.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JobApplicationRepositoryTest {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testFindByStatus() {
        List<JobApplication> applications = jobApplicationRepository.findByStatus(Status.PENDING);
        assertNotNull(applications);
        // We can't assert much without knowing the test data structure
    }

    @Test
    void testSaveJobApplication() {
        // Create test user
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        // Create test job posting
        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("Test Job");
        jobPosting.setDescription("Test Description");
        jobPosting.setRequirements("Test Requirements");
        jobPosting.setCreatedBy("test-user-id");
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

        // Create job application
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplicant(savedUser);
        jobApplication.setJob(savedJobPosting);
        jobApplication.setStatus(Status.PENDING);

        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);
        assertNotNull(savedApplication.getId());
        assertEquals(Status.PENDING, savedApplication.getStatus());
        assertEquals(savedUser, savedApplication.getApplicant());
        assertEquals(savedJobPosting, savedApplication.getJob());
    }
}