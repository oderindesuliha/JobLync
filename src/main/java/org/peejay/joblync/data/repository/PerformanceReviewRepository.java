package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.PerformanceReview;
import org.peejay.joblync.data.models.ReviewType;
import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, String> {
    List<PerformanceReview> findByEmployee(User employee);
    List<PerformanceReview> findByReviewer(User reviewer);
    List<PerformanceReview> findByEmployeeAndReviewType(User employee, ReviewType reviewType);
}