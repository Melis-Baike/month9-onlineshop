package com.example.onlineshop.service;

import com.example.onlineshop.DTO.GuestDTO;
import com.example.onlineshop.entity.Authority;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.AuthorityRepository;
import com.example.onlineshop.repository.UserRepository;
import com.example.onlineshop.security.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public GuestDTO createGuest(){
        Random rnd = new Random();
        boolean isFound = false;
        User guest = null;
        String password = null;
        while (!isFound){
            int rndNumber = rnd.nextInt(1000000000);
            String name = "Guest";
            String email = "guest" + rndNumber + "@gmail.com";
            password = "guest" + rndNumber;
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isEmpty()){
                isFound = true;
                Authority authority = authorityRepository.findById(1L).get();
                guest = User.builder()
                        .name(name)
                        .email(email)
                        .password(SecurityConfiguration.passwordEncoder().encode(password))
                        .enabled(true)
                        .authority(authority)
                        .build();
                userRepository.save(guest);
            }
        }
        return GuestDTO.from(guest.getEmail(), password);
    }
}
