package com.example.onlineshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    private ResponseEntity<String> handleBind(BindException exc){
        return ResponseEntity.badRequest().body("Not valid data");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleNotValid(MethodArgumentNotValidException exc){
        return ResponseEntity.badRequest().body("Not valid data");
    }
}
