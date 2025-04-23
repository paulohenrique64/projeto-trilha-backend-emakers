package com.paulohenrique.library.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccountDeleteRequestDto(
        @NotBlank(message = "Password is required to delete the account")
        String password
) {}