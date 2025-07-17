package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.HRManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface HRManagerRepository extends JpaRepository<HRManager, Long> {
    HRManager findByEmail(String email);
    HRManager findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

}
