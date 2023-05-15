package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        return new ResponseEntity<>(userService.register(userRegistrationDTO), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public UserDTO login(Authentication authentication){
        return userService.login(authentication);
    }
}
