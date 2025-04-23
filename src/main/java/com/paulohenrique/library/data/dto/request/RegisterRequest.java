package com.paulohenrique.library.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email must be in valid format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 50, message = "Name must be between 8 and 50 characters")
        String password,

        @NotBlank(message = "CEP code is required")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP code must be in the format 12345-678")
        String cep
) {}
