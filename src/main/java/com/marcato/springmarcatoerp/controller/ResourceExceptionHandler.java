package com.marcato.springmarcatoerp.controller;

import com.marcato.springmarcatoerp.DTO.API.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExists(HttpClientErrorException.Conflict ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500
    }
}

