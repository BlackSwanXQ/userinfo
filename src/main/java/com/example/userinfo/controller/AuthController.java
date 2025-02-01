package com.example.userinfo.controller;

import com.example.userinfo.auth.AuthService;
import com.example.userinfo.auth.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService = new AuthService();


    /**
     * Аутетификация пользователя
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (authService.authenticate(username, password)) {
            return JwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    /**
     * Проверка токена
     */
    @GetMapping("/secure")
    public String secureEndpoint(@RequestHeader("Authorization") String token) {
        if (JwtUtil.validateToken(token)) {
            String username = JwtUtil.getUsernameFromToken(token);
            return "Hello, " + username + "! This is a secure endpoint.";
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}