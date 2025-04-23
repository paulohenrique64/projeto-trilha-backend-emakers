package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.response.GeneralResponseDto;
import com.paulohenrique.library.exception.LibraryApiException;
import com.paulohenrique.library.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestControllerExceptionHandler {
    //
    // Tratando as exceptions lançadas por mim
    //
    @ExceptionHandler(LibraryApiException.class)
    public ResponseEntity<GeneralResponseDto> handleLibraryApiException(LibraryApiException e) {
        return ResponseEntity.status(e.getStatus()).body(new GeneralResponseDto(false, e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GeneralResponseDto> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDto(false, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GeneralResponseDto> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDto(false, e.getMessage()));
    }

    //
    // Tratando as exceptions lançadas pelo Spring
    //
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorMessage = "The request body could not be parsed";
        return ResponseEntity.badRequest().body(new GeneralResponseDto(false, errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponseDto> handleValidationErrors(MethodArgumentNotValidException e) {
        var fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = "Request Body is invalid";

        for (FieldError fieldError : fieldErrors) {
            if (fieldError.getDefaultMessage() != null) {
                errorMessage = fieldError.getDefaultMessage();
            }
        }

        return ResponseEntity.badRequest().body(new GeneralResponseDto(false, errorMessage));
    }
}
