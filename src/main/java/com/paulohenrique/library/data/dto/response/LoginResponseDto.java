package com.paulohenrique.library.data.dto.response;

public record LoginResponseDto(boolean success, String message, String token, UserResponseDto data) {
}