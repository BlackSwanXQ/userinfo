package com.example.userinfo.auth;

import com.example.userinfo.auth.AuthService;
import com.example.userinfo.auth.JwtUtil;

public class AuthController {
    private AuthService authService = new AuthService();

    public String login(String username, String password) {
        if (authService.authenticate(username, password)) {
            return JwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}