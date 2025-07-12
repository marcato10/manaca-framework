package com.marcato.springmarcatoerp.DTO.Workspace;

import com.marcato.springmarcatoerp.DTO.Views.ViewDTO;
import com.marcato.springmarcatoerp.entity.tables.records.ViewsRecord;
import org.jooq.Converter;

import java.util.Map;
import java.util.UUID;

public record WorkspaceViewsDTO(UUID id, Map<UUID, ViewDTO> views) {
}
