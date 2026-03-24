package com.example.demo.duanthuctap.service.impl;

import com.example.demo.duanthuctap.dto.AuthRequest;
import com.example.demo.duanthuctap.dto.AuthResponse;
import com.example.demo.duanthuctap.entity.Role;
import com.example.demo.duanthuctap.entity.UserEntity;
import com.example.demo.duanthuctap.exception.BusinessException;
import com.example.demo.duanthuctap.repository.UserRepository;
import com.example.demo.duanthuctap.security.JwtUtil;
import com.example.demo.duanthuctap.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(AuthRequest request) {

        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new BusinessException("Username is required");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BusinessException("Password is required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}