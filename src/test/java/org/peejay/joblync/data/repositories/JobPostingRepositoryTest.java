//package org.peejay.joblync.data.repositories;
//
//import org.junit.jupiter.api.Test;
//import org.peejay.joblync.data.models.JobPosting;
//import org.peejay.joblync.data.models.JobStatus;
//import org.peejay.joblync.data.models.Recruiter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Transactional
//public class JobPostingRepositoryTest {
//
//    @Autowired
//    private JobPostingRepository jobPostingRepository;
//
//    @Test
//    void testThatSavesJobPosting_AndFindsById() {
//        JobPosting job = new JobPosting();
//        job.setJobTitle("Backend Developer");
//        job.setCompanyName("TechCorps");
//        job.setJobDescription("Build REST APIs");
//        job.setStatus(JobStatus.OPEN);
//        job.setPostDate(LocalDateTime.now());
//        job.setDeadLine(LocalDateTime.now().plusDays(7));
//        job.setPostedBy(new Recruiter());
//
//        JobPosting saved = jobPostingRepository.save(job);
//        Optional<JobPosting> result = jobPostingRepository.findById(saved.getId());
//
//        assertTrue(result.isPresent());
//        assertEquals("Backend Developer", result.get().getJobTitle());
//        assertEquals(JobStatus.OPEN, result.get().getStatus());
//    }
//
//    @Test
//    void testFindByCompanyName() {
//        JobPosting job = new JobPosting();
//        job.setJobTitle("DevOps Engineer");
//        job.setCompanyName("TechCorps");
//        job.setStatus(JobStatus.OPEN);
//        job.setPostDate(LocalDateTime.now());
//        job.setDeadLine(LocalDateTime.now().plusDays(5));
//        job.setPostedBy(new Recruiter());
//
//        jobPostingRepository.save(job);
//
//        List<JobPosting> results = jobPostingRepository.findByCompanyName("TechCorps");
//
//        assertEquals(1, results.size());
//        assertEquals("TechCorps", results.get(0).getCompanyName());
//    }
//
//    @Test
//    void testFindByCompanyNameAndJobTitle() {
//        JobPosting job = new JobPosting();
//        job.setJobTitle("Backend Developer");
//        job.setCompanyName("TechCorps");
//        job.setStatus(JobStatus.OPEN);
//        job.setPostDate(LocalDateTime.now());
//        job.setDeadLine(LocalDateTime.now().plusDays(10));
//        job.setPostedBy(new Recruiter());
//
//        jobPostingRepository.save(job);
//
//        List<JobPosting> results = jobPostingRepository.findByCompanyNameAndJobTitle("TechCorps", "Backend Developer");
//
//        assertEquals(1, results.size());
//        assertEquals("Backend Developer", results.get(0).getJobTitle());
//    }
//
//    @Test
//    void testFindByStatus() {
//        JobPosting job = new JobPosting();
//        job.setJobTitle("QA Engineer");
//        job.setCompanyName("Testify Inc.");
//        job.setStatus(JobStatus.CANCELLED);
//        job.setPostDate(LocalDateTime.now());
//        job.setDeadLine(LocalDateTime.now().plusDays(3));
//        job.setPostedBy(new Recruiter());
//
//        jobPostingRepository.save(job);
//
//        Optional<JobPosting> result = jobPostingRepository.findByStatus(JobStatus.CANCELLED.toString());
//
//        assertTrue(result.isPresent());
//        assertEquals(JobStatus.CANCELLED, result.get().getStatus());
//    }
//}
