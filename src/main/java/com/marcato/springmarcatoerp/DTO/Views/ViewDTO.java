package com.marcato.springmarcatoerp.DTO.Views;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.jooq.JSONB;

import java.util.UUID;

public record ViewDTO(@NotBlank UUID viewID,
                      @NotBlank @Size(max = 50)
                      String label,
                      @NotBlank
                      JSONB data,
                      @NotBlank
                      String entityKey,
                      @NotBlank
                      UUID workspaceUUID) {
}
