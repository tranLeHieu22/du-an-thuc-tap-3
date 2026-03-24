package com.example.demo.duanthuctap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestEncode {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}