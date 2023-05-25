package com.example.onlineshop.controller;

import com.example.onlineshop.DTO.OrderDTO;
import com.example.onlineshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{basketProductId}")
    public ResponseEntity<OrderDTO> checkout(HttpSession session, @PathVariable @Valid @Min(value = 1) @NotNull
                                            Long basketProductId){
        return new ResponseEntity<>(orderService.checkout(session, basketProductId), HttpStatus.OK);
    }

    @GetMapping("/check/{productName}")
    public ResponseEntity<Boolean> check(HttpSession session, @PathVariable @Valid @NotBlank String productName){
        return new ResponseEntity<>(orderService.check(session, productName), HttpStatus.OK);
    }
}
