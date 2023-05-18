package com.example.onlineshop.service;

import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserLoginDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.AuthorityRepository;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.security.SecurityConfiguration;
import com.example.onlineshop.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;


    public String register(UserRegistrationDTO userRegistrationDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(optionalUser.isEmpty()) {
            User user = User.builder()
                    .name(userRegistrationDTO.getName())
                    .email(userRegistrationDTO.getEmail())
                    .password(SecurityConfiguration.passwordEncoder().encode(userRegistrationDTO.getPassword()))
                    .enabled(true)
                    .authority(authorityRepository.findById(2L).get())
                    .build();
            userRepository.save(user);
            return "You have successfully registered";
        }
        return "Enter another email";
    }

    public boolean login(UserLoginDTO userLoginDTO){
        Optional<User> user = userRepository.findByEmail(userLoginDTO.getEmail());
        return user.filter(value -> PasswordUtils.checkPassword(userLoginDTO.getPassword(), value.getPassword())).isPresent();
    }

    public UserDTO login(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).get();
        return UserDTO.from(user);
    }
}
