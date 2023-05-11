package com.example.onlineshop.service;

import com.example.onlineshop.security.SecurityConfiguration;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.DTO.UserRegistrationDTO;
import com.example.onlineshop.entity.Role;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.entity.UserRole;
import com.example.onlineshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    public String register(UserRegistrationDTO userRegistrationDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(optionalUser.isEmpty()) {
            User user = User.builder()
                    .name(userRegistrationDTO.getName())
                    .email(userRegistrationDTO.getEmail())
                    .password(SecurityConfiguration.passwordEncoder().encode(userRegistrationDTO.getPassword()))
                    .enabled(true)
                    .build();
            Optional<Role> role = roleRepository.findById(2L);
            if (role.isPresent()) {
                UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(role.get())
                        .build();
                userRepository.save(user);
                userRoleRepository.save(userRole);
                return "You have successfully registered";
            }
        }
        return "Something went wrong";
    }

    public UserDTO login(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).get();
        return UserDTO.from(user);
    }
}
