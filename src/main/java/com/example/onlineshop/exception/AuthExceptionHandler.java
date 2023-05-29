package com.example.onlineshop.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {
    private final ErrorLocalization errorLocalization;

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        String errorCode = "bad_credentials_error";
        String errorMessage = errorLocalization.getLocalizedErrorMessage(errorCode);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
