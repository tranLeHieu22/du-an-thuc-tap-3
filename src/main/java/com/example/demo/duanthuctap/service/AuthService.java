package com.example.demo.duanthuctap.service;

import com.example.demo.duanthuctap.dto.AuthRequest;
import com.example.demo.duanthuctap.dto.AuthResponse;

public interface AuthService {

    void register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}