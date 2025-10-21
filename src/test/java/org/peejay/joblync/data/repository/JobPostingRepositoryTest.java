package org.peejay.joblync.data.repository;

import org.junit.jupiter.api.Test;
import org.peejay.joblync.data.models.JobPosting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobPostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testFindByCreatedBy() {
        List<JobPosting> jobPostings = jobPostingRepository.findByCreatedBy("2");
        assertNotNull(jobPostings);
        assertFalse(jobPostings.isEmpty());
        assertEquals(2, jobPostings.size());
    }

    @Test
    void testSaveJobPosting() {
        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle("Test Job");
        jobPosting.setDescription("Test Description");
        jobPosting.setRequirements("Test Requirements");
        jobPosting.setCreatedBy("1");

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        assertNotNull(savedJobPosting.getId());
        assertEquals("Test Job", savedJobPosting.getTitle());
        assertEquals("Test Description", savedJobPosting.getDescription());
        assertEquals("Test Requirements", savedJobPosting.getRequirements());
        assertEquals("1", savedJobPosting.getCreatedBy());
    }
}
