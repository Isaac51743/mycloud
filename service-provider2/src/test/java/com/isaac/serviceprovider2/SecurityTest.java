package com.isaac.serviceprovider2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void createEncodedPassword() {
        System.out.println(passwordEncoder.encode("isaac"));
        System.out.println(passwordEncoder.encode("barry"));
        System.out.println(passwordEncoder.matches("isaac", "$2a$10$/0C4hV1rjc86kE6G1CqRbOTSpsrq529lKIa2dZOkGsgU2ucQhNwWu"));
    }
}
