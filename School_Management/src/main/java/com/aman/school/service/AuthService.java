package com.aman.school.service;

import com.aman.school.dto.AuthRequest;
import com.aman.school.dto.AuthResponse;
import com.aman.school.entity.Role;
import com.aman.school.entity.User;
import com.aman.school.repository.UserRepository;
import com.aman.school.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse registerStudent(AuthRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return new AuthResponse(req.getUsername(), null, "username exists");
        }
        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Collections.singleton(Role.ROLE_STUDENT))
                .build();
        userRepository.save(u);
        String token = jwtUtil.generateToken(u.getUsername());
        return new AuthResponse(u.getUsername(), token, "registered");
    }

    public AuthResponse login(AuthRequest req) {
        var opt = userRepository.findByUsername(req.getUsername());
        if (opt.isEmpty()) return new AuthResponse(req.getUsername(), null, "invalid credentials");
        User user = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return new AuthResponse(req.getUsername(), null, "invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(user.getUsername(), token, "login success");
    }
}
