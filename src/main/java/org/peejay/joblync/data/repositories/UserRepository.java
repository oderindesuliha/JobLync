package org.peejay.joblync.data.repositories;

import org.peejay.joblync.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);


    Optional<User> findUsersByEmail(String email);
}