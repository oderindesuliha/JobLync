package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.PerformanceReview;
import org.peejay.joblync.data.models.ReviewType;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.PerformanceReviewRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.PerformanceReviewRequest;
import org.peejay.joblync.services.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public PerformanceReviewServiceImpl(PerformanceReviewRepository performanceReviewRepository, UserRepository userRepository) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PerformanceReview createPerformanceReview(PerformanceReviewRequest request) {
        PerformanceReview performanceReview = new PerformanceReview();
        
        // Set employee
        Optional<User> employee = userRepository.findById(request.getEmployeeId());
        if (employee.isPresent()) {
            performanceReview.setEmployee(employee.get());
        } else {
            throw new RuntimeException("Employee not found with id: " + request.getEmployeeId());
        }
        
        // Set reviewer
        if (request.getReviewerId() != null) {
            Optional<User> reviewer = userRepository.findById(request.getReviewerId());
            if (reviewer.isPresent()) {
                performanceReview.setReviewer(reviewer.get());
            } else {
                throw new RuntimeException("Reviewer not found with id: " + request.getReviewerId());
            }
        }
        
        performanceReview.setRating(request.getRating());
        performanceReview.setComments(request.getComments());
        performanceReview.setGoals(request.getGoals());
        performanceReview.setAreasForImprovement(request.getAreasForImprovement());
        performanceReview.setReviewType(request.getReviewType());
        performanceReview.setNextReviewDate(request.getNextReviewDate());
        
        return performanceReviewRepository.save(performanceReview);
    }

    @Override
    public Optional<PerformanceReview> findPerformanceReviewById(String id) {
        return performanceReviewRepository.findById(id);
    }

    @Override
    public List<PerformanceReview> findPerformanceReviewsByEmployee(String employeeId) {
        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isPresent()) {
            return performanceReviewRepository.findByEmployee(employee.get());
        }
        throw new RuntimeException("Employee not found with id: " + employeeId);
    }

    @Override
    public List<PerformanceReview> findPerformanceReviewsByReviewer(String reviewerId) {
        Optional<User> reviewer = userRepository.findById(reviewerId);
        if (reviewer.isPresent()) {
            return performanceReviewRepository.findByReviewer(reviewer.get());
        }
        throw new RuntimeException("Reviewer not found with id: " + reviewerId);
    }

    @Override
    public List<PerformanceReview> findPerformanceReviewsByEmployeeAndType(String employeeId, ReviewType reviewType) {
        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isPresent()) {
            return performanceReviewRepository.findByEmployeeAndReviewType(employee.get(), reviewType);
        }
        throw new RuntimeException("Employee not found with id: " + employeeId);
    }

    @Override
    public PerformanceReview updatePerformanceReview(String id, PerformanceReviewRequest request) {
        Optional<PerformanceReview> existingReview = performanceReviewRepository.findById(id);
        if (existingReview.isPresent()) {
            PerformanceReview performanceReview = existingReview.get();
            
            // Update employee if provided
            if (request.getEmployeeId() != null) {
                Optional<User> employee = userRepository.findById(request.getEmployeeId());
                if (employee.isPresent()) {
                    performanceReview.setEmployee(employee.get());
                } else {
                    throw new RuntimeException("Employee not found with id: " + request.getEmployeeId());
                }
            }
            
            // Update reviewer if provided
            if (request.getReviewerId() != null) {
                Optional<User> reviewer = userRepository.findById(request.getReviewerId());
                if (reviewer.isPresent()) {
                    performanceReview.setReviewer(reviewer.get());
                } else {
                    throw new RuntimeException("Reviewer not found with id: " + request.getReviewerId());
                }
            }
            
            performanceReview.setRating(request.getRating());
            performanceReview.setComments(request.getComments());
            performanceReview.setGoals(request.getGoals());
            performanceReview.setAreasForImprovement(request.getAreasForImprovement());
            performanceReview.setReviewType(request.getReviewType());
            performanceReview.setNextReviewDate(request.getNextReviewDate());
            
            return performanceReviewRepository.save(performanceReview);
        }
        throw new RuntimeException("Performance review not found with id: " + id);
    }

    @Override
    public void deletePerformanceReview(String id) {
        performanceReviewRepository.deleteById(id);
    }
}