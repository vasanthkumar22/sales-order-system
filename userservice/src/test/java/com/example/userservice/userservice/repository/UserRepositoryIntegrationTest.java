package com.example.userservice.userservice.repository;

import com.example.userservice.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindByUsername(){

        User user = new User();
        user.setName("Colin But");
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setEmail("colin.but@outlook.com");
        user.setRoles(String.join(",", "ADMIN"));
        userRepository.save(user);
        User user1 = userRepository.findByUsername("colinbut");
        assertEquals("colinbut", user1.getUsername());
        assertEquals("password", user1.getPassword());
        assertEquals("Colin But", user1.getName());
        assertEquals("colin.but@outlook.com", user1.getEmail());
        assertEquals("ADMIN", user1.getRoles());
    }
}
