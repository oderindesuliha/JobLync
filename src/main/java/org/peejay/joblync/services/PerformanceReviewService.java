package org.peejay.joblync.services;

import org.peejay.joblync.data.models.PerformanceReview;
import org.peejay.joblync.data.models.ReviewType;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.PerformanceReviewRequest;

import java.util.List;
import java.util.Optional;

public interface PerformanceReviewService {
    PerformanceReview createPerformanceReview(PerformanceReviewRequest request);
    Optional<PerformanceReview> findPerformanceReviewById(String id);
    List<PerformanceReview> findPerformanceReviewsByEmployee(String employeeId);
    List<PerformanceReview> findPerformanceReviewsByReviewer(String reviewerId);
    List<PerformanceReview> findPerformanceReviewsByEmployeeAndType(String employeeId, ReviewType reviewType);
    PerformanceReview updatePerformanceReview(String id, PerformanceReviewRequest request);
    void deletePerformanceReview(String id);
}