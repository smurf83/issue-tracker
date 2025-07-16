package com.example.issuetracker.config;

import com.example.issuetracker.model.Role;
import com.example.issuetracker.model.User;
import com.example.issuetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            seedUsers();
            log.info("Seed data has been created successfully");
        } else {
            log.info("Seed data already exists, skipping...");
        }
    }

    private void seedUsers() {
        // Create Admin user
        User admin = User.builder()
                .email("admin@example.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();

        // Create Developer user
        User developer = User.builder()
                .email("dev@example.com")
                .password(passwordEncoder.encode("dev"))
                .role(Role.DEVELOPER)
                .build();

        // Create Tester user
        User tester = User.builder()
                .email("tester@example.com")
                .password(passwordEncoder.encode("tester"))
                .role(Role.TESTER)
                .build();

        userRepository.save(admin);
        userRepository.save(developer);
        userRepository.save(tester);

        log.info("Created seed users:");
        log.info("Admin: admin@example.com / admin");
        log.info("Developer: dev@example.com / dev");
        log.info("Tester: tester@example.com / tester");
    }
}