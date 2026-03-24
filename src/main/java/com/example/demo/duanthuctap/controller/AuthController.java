package com.example.demo.duanthuctap.controller;

import com.example.demo.duanthuctap.dto.AuthRequest;
import com.example.demo.duanthuctap.dto.AuthResponse;
import com.example.demo.duanthuctap.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        authService.register(request);
        return "Register success";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}