package com.marcato.springmarcatoerp.DTO.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentInputDTO(
        Integer id,
        @NotBlank
        @Size(max = 50, message = "Title cannot exceed 50 characters")
        String title, String description) {
}
