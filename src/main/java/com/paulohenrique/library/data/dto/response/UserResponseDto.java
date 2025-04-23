package com.paulohenrique.library.data.dto.response;

public record UserResponseDto(int id, String name, String email, String cep, String state, String role) {
}
