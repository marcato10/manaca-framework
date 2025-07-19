package com.marcato.springmarcatoerp.DTO.Workspace;

import com.marcato.springmarcatoerp.DTO.Views.ViewDTO;

import java.util.Map;
import java.util.UUID;

public record WorkspaceViewsDTO(UUID id, Map<UUID, ViewDTO> views) {
}
