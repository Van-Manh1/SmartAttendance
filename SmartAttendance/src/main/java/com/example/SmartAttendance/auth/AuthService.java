package com.example.SmartAttendance.auth;

import com.example.SmartAttendance.entity.Role;
import com.example.SmartAttendance.entity.User;
import com.example.SmartAttendance.repository.RoleRepository;
import com.example.SmartAttendance.repository.UserRepository;
import com.example.SmartAttendance.security.CustomUserDetails;
import com.example.SmartAttendance.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(String username,
                           String password,
                           String fullName) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        Role roleUser = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("Role USER not found"));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .roles(Set.of(roleUser))
                .build();

        userRepository.save(user);

        return jwtService.generateToken(
                new CustomUserDetails(user));
    }

    public String login(String username,
                        String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password));

        User user = userRepository
                .findByUsername(username)
                .orElseThrow();

        return jwtService.generateToken(
                new CustomUserDetails(user));
    }
}