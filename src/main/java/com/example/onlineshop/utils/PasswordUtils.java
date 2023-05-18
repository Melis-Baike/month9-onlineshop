package com.example.onlineshop.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}