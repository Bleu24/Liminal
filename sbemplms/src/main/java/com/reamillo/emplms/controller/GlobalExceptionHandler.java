package com.reamillo.emplms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleSecurityException(Exception exception) {
        Errors errorResponse;
        HttpStatus status;

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "The username or password is incorrect");
        } else if (exception instanceof AccountStatusException) {
            status = HttpStatus.FORBIDDEN;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "The account is locked");
        } else if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "You are not authorized to access this resource");
        } else if (exception instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "The JWT signature is invalid");
        } else if (exception instanceof ExpiredJwtException) {
            status = HttpStatus.FORBIDDEN;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "The JWT token has expired");
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponse = new Errors(
                status.value(), exception.getMessage(), "Unknown internal server error.");
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
