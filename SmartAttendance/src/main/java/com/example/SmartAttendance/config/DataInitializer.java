package com.example.SmartAttendance.config;

import com.example.SmartAttendance.entity.Role;
import com.example.SmartAttendance.entity.User;
import com.example.SmartAttendance.repository.RoleRepository;
import com.example.SmartAttendance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init() {
        return args -> {

            // Tạo roles nếu chưa có
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() ->
                            roleRepository.save(Role.builder()
                                    .name("ROLE_ADMIN")
                                    .build()));

            Role managerRole = roleRepository.findByName("ROLE_MANAGER")
                    .orElseGet(() ->
                            roleRepository.save(Role.builder()
                                    .name("ROLE_MANAGER")
                                    .build()));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() ->
                            roleRepository.save(Role.builder()
                                    .name("ROLE_USER")
                                    .build()));

            // Tạo admin mặc định
            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .fullName("System Admin")
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
            }
        };
    }
}