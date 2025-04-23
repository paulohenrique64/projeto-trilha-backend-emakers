package com.paulohenrique.library.data.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookEditRequestDto(
        String title,
        String author,
        @PastOrPresent(message = "Release date cannot be in the future")
        LocalDate releaseDate
) {}