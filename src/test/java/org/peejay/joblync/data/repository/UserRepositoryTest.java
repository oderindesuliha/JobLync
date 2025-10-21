package org.peejay.joblync.data.repository;

import org.junit.jupiter.api.Test;
import org.peejay.joblync.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @Sql(scripts = {"/db/data.sql"})
    void testFindByEmail() {
        Optional<User> user = userRepository.findByEmail("adah@gmail.com");
        User accountUser = user.orElseThrow(RuntimeException::new);
        assertNotNull(accountUser);

    }

}