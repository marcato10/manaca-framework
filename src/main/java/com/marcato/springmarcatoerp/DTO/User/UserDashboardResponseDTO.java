package com.marcato.springmarcatoerp.DTO.User;

import com.marcato.springmarcatoerp.DTO.Workspace.WorkspaceViewsDTO;

import java.util.List;

public record UserDashboardResponseDTO(UserDTO user,
                                       List<WorkspaceViewsDTO> workspaces
) { }
