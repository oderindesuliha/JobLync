package org.peejay.joblync.data.repository;

import org.peejay.joblync.data.models.Role;
import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    List<User> findByDepartment(String department);
    List<User> findByRole(Role role);
    List<User> findByManagerId(String managerId);
}