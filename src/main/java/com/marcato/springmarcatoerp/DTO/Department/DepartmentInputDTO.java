package com.marcato.springmarcatoerp.DTO.Department;

import com.marcato.springmarcatoerp.jooq.tables.pojos.DepartmentPojo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentInputDTO(
        @NotBlank
        @Size(max = 50, message = "Title cannot exceed 50 characters")
        String title, String description)  {
        public DepartmentInputDTO(@NotBlank
                                  @Size(max = 50, message = "Title cannot exceed 50 characters")
                                  String title, String description) {
                this.title = title;
                this.description = description;
        }
}
