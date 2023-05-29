package com.example.onlineshop.controller;

import com.example.onlineshop.exception.ErrorLocalization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {
    private final ErrorLocalization errorLocalization;

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    private ResponseEntity<String> handleBind(BindException exc) {
        String errorMessage = exc.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleNotValid(MethodArgumentNotValidException exc) {
        String errorMessage = exc.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            return errorLocalization.getLocalizedErrorMessage(fieldError.getDefaultMessage());
        } else {
            return errorLocalization.getLocalizedErrorMessage(error.getDefaultMessage());
        }
    }
}



