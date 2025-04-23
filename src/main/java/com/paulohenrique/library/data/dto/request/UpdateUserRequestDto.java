package com.paulohenrique.library.data.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        String password,

        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP code must be in the format 12345-678")
        String cep
) {}