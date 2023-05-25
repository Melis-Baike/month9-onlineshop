package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.GuestDTO;
import com.example.onlineshop.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @PostMapping()
    public ResponseEntity<GuestDTO> createGuestAccount(){
        return new ResponseEntity<>(guestService.createGuest(), HttpStatus.OK);
    }
}
