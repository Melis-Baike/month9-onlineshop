package com.example.onlineshop.service;

import com.example.onlineshop.DTO.PasswordRecoveryDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.AuthorityRepository;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.security.SecurityConfiguration;
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

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserDTO login(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).get();
        return UserDTO.from(user);
    }

    public boolean recoveryPassword(PasswordRecoveryDTO passwordRecoveryDTO){
        Optional<User> optionalUser = userRepository.findByEmail(passwordRecoveryDTO.getEmail());
        if(optionalUser.isPresent()){
            if(passwordRecoveryDTO.getNewPassword().equals(passwordRecoveryDTO.getConfirmPassword())) {
                optionalUser.get().setPassword(passwordRecoveryDTO.getNewPassword());
                userRepository.setPasswordById(SecurityConfiguration.passwordEncoder()
                        .encode(optionalUser.get().getPassword()), optionalUser.get().getId());
                return true;
            }
        }
        return false;
    }
}
