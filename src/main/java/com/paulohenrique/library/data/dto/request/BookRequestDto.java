package com.paulohenrique.library.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record BookRequestDto(
        @NotBlank(message = "Book title is required")
        String title,

        @NotBlank(message = "Author name is required")
        String author,

        @PastOrPresent(message = "Release date cannot be in the future")
        LocalDate releaseDate
) {}