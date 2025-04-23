package com.paulohenrique.library.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,

        @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email must be in valid format")
        String email,

        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        String password
) {}