package com.paulohenrique.library.data.dto.response;

public record RegisterResponseDto (boolean success, String message, UserResponseDto data) {
}
