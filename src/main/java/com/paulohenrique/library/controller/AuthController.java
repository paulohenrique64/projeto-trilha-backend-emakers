package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.LoginRequest;
import com.paulohenrique.library.data.dto.request.RegisterRequest;
import com.paulohenrique.library.data.dto.response.LoginResponseDto;
import com.paulohenrique.library.data.dto.response.RegisterResponseDto;
import com.paulohenrique.library.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Validated RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}

