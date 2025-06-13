package com.marcato.springmarcatoerp.DTO.User;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationDTO(
        @NotBlank
        String userName,
        @NotBlank
        String fullName) {
}
