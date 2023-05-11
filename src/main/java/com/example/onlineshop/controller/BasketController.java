package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.BasketProductDTO;
import com.example.onlineshop.DTO.OrderProductDTO;
import com.example.onlineshop.service.BasketProductService;
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
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketProductService basketProductService;

    @PostMapping("/ordering")
    public ResponseEntity<BasketProductDTO> putProductInBasket(Authentication authentication,
                                                        @Valid @RequestBody OrderProductDTO orderProductDTO){
        BasketProductDTO basketProductDTO = basketProductService.putInTheBasket(authentication, orderProductDTO.getName(),
                orderProductDTO.getQuantity());
        if(basketProductDTO == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(basketProductDTO, HttpStatus.OK);
        }
    }
}
