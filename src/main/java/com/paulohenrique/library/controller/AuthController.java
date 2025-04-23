package com.paulohenrique.library.controller;

import com.paulohenrique.library.data.dto.request.LoginRequest;
import com.paulohenrique.library.data.dto.request.RegisterRequest;
import com.paulohenrique.library.data.dto.response.LoginResponseDto;
import com.paulohenrique.library.data.dto.response.RegisterResponseDto;
import com.paulohenrique.library.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Realiza o login do usuário",
            description = "Autentica o usuário com e-mail e senha e retorna um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Registra um novo usuário",
            description = "Cria um novo usuário com nome, e-mail e senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = RegisterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: e-mail já em uso)",
                    content = @Content)
    })
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Validated RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}

