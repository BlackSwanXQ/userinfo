package com.example.userinfo.auth;

public class AuthService {
    public boolean authenticate(String username, String password) {
        // Проверка логина и пароля
        return "admin".equals(username) && "admin".equals(password);
    }
}
